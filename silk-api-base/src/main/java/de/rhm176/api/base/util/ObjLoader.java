package de.rhm176.api.base.util;

import de.javagl.obj.FloatTuple;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjFace;
import de.javagl.obj.ObjGroup;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;
import org.lwjgl.util.vector.Vector3f;
import picking.AABB;
import blueprints.SubBlueprint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public final class ObjLoader {
    private static final Vector3f DEFAULT_COLOR = new Vector3f(0.8f, 0.8f, 0.8f);
    private static final float DEFAULT_WOBBLE_FACTOR = 0.0f;

    public record SectionData(int vertexCount, Vector3f colour, float wobbleFactor, String materialName) {

    }

    private static class IntermediateModelData {
        public Vector3f mins;
        public Vector3f maxs;
        public AABB aabb;
        public float increaseFactor;
        public AABB[] extraAabbs;
        public float[] modelData;
        public List<SectionData> sections;

        public IntermediateModelData() {
            this.mins = new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
            this.maxs = new Vector3f(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
            this.increaseFactor = 1.0f;
            this.extraAabbs = null;
            this.sections = new ArrayList<>();
        }

        public void updateBounds(Vector3f point) {
            mins.x = Math.min(mins.x, point.x);
            mins.y = Math.min(mins.y, point.y);
            mins.z = Math.min(mins.z, point.z);
            maxs.x = Math.max(maxs.x, point.x);
            maxs.y = Math.max(maxs.y, point.y);
            maxs.z = Math.max(maxs.z, point.z);
        }

        public void finalizeAABB() {
            if (this.mins.x > this.maxs.x) {
                this.aabb = new AABB(new Vector3f(0,0,0), new Vector3f(0,0,0));
            } else {
                this.aabb = new AABB(this.mins, this.maxs);
            }
        }
    }

    public static class MaterialInfo {
        public String name;
        public Vector3f diffuseColor;
        public float wobbleFactor;

        public MaterialInfo(String name) {
            this.name = name;
            this.diffuseColor = new Vector3f(DEFAULT_COLOR.x, DEFAULT_COLOR.y, DEFAULT_COLOR.z);
            this.wobbleFactor = DEFAULT_WOBBLE_FACTOR;
        }
    }

    public static Map<String, MaterialInfo> parseMtlFile(InputStream mtlInputStream, String mtlNameForLogging) throws IOException {
        Map<String, MaterialInfo> materialInfos = new HashMap<>();
        if (mtlInputStream == null) {
            System.err.println("MTL InputStream is null for " + mtlNameForLogging + ". Using default material properties.");
            return materialInfos;
        }

        MaterialInfo currentMaterial = null;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(mtlInputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("\\s+");
                if (parts.length == 0) {
                    continue;
                }

                String directive = parts[0].toLowerCase(Locale.ROOT);

                switch (directive) {
                    case "newmtl":
                        if (parts.length >= 2) {
                            currentMaterial = new MaterialInfo(parts[1]);
                            materialInfos.put(parts[1], currentMaterial);
                        } else {
                            System.err.println("Warning: Malformed newmtl line in " + mtlNameForLogging + ": '" + line + "'");
                        }
                        break;
                    case "kd":
                        if (currentMaterial != null && parts.length >= 4) {
                            try {
                                float r = Float.parseFloat(parts[1]);
                                float g = Float.parseFloat(parts[2]);
                                float b = Float.parseFloat(parts[3]);
                                currentMaterial.diffuseColor.set(r, g, b);
                            } catch (NumberFormatException e) {
                                System.err.println("Warning: Could not parse Kd (diffuse color) in " + mtlNameForLogging + " for material " + currentMaterial.name + ": '" + line + "'");
                            }
                        }
                        break;
                    case "wobblefactor":
                        if (currentMaterial != null && parts.length >= 2) {
                            try {
                                currentMaterial.wobbleFactor = Float.parseFloat(parts[1]);
                            } catch (NumberFormatException e) {
                                System.err.println("Warning: Could not parse wobbleFactor in " + mtlNameForLogging + " for material " + currentMaterial.name + ": '" + line + "'");
                            }
                        }
                        break;
                }
            }
        }
        return materialInfos;
    }

    public static SubBlueprint convertToSubBlueprint(InputStream objInputStream, String objName, InputStream mtlInputStream, String mtlName, float size, float defaultIncreaseFactor) throws IOException {
        if (objInputStream == null) {
            throw new IOException("OBJ InputStream cannot be null for " + objName);
        }
        Map<String, MaterialInfo> materialColorMap = parseMtlFile(mtlInputStream, mtlName);
        IntermediateModelData intermediateData = new IntermediateModelData();
        intermediateData.increaseFactor = defaultIncreaseFactor;

        Obj obj = ObjReader.read(objInputStream);
        Obj triangulatedObj = ObjUtils.triangulate(obj);

        List<Float> modelDataList = new ArrayList<>();

        for (int k = 0; k < triangulatedObj.getNumMaterialGroups(); k++) {
            ObjGroup materialGroup = triangulatedObj.getMaterialGroup(k);
            String materialGroupName = materialGroup.getName();
            processGroup(materialGroup, materialGroupName, triangulatedObj, materialColorMap, size, intermediateData, modelDataList);
        }

        intermediateData.modelData = new float[modelDataList.size()];
        for (int i = 0; i < modelDataList.size(); i++) {
            intermediateData.modelData[i] = modelDataList.get(i);
        }
        intermediateData.finalizeAABB();

        return new SubBlueprint(
                intermediateData.modelData,
                intermediateData.aabb,
                intermediateData.extraAabbs,
                intermediateData.increaseFactor
        );
    }

    private static void processGroup(ObjGroup group, String groupName, Obj triangulatedObj,
                                     Map<String, MaterialInfo> materialInfoMap, float size,
                                     IntermediateModelData intermediateData, List<Float> modelDataList) {
        if (group == null || group.getNumFaces() == 0) {
            return;
        }

        MaterialInfo matInfo = materialInfoMap.get(groupName);
        if (matInfo == null) {
            matInfo = new MaterialInfo(groupName);
            System.err.println("Warning: Material '" + groupName + "' not found in MTL. Using default properties.");
        }

        List<Float> sectionModelDataFloats = new ArrayList<>();
        int sectionVertexCount = 0;

        for (int i = 0; i < group.getNumFaces(); i++) {
            ObjFace face = group.getFace(i);

            for (int j = 0; j < face.getNumVertices(); j++) {
                int vertexIndex = face.getVertexIndex(j);
                int normalIndex = face.getNormalIndex(j);

                if (vertexIndex < 0 || vertexIndex >= triangulatedObj.getNumVertices()) {
                    System.err.println("Warning: Invalid vertex index ("+vertexIndex+") in face from group '" + groupName + "'. Max vertices: " + triangulatedObj.getNumVertices() + ". Skipping vertex.");
                    continue;
                }

                Vector3f normal;
                if (!face.containsNormalIndices() || normalIndex < 0 || normalIndex >= triangulatedObj.getNumNormals()) {
                    System.err.println("Warning: Face does not contain normal indices or normal index ("+normalIndex+") is invalid in group '" + groupName + "'. Max normals: " + triangulatedObj.getNumNormals() + ". Using default normal (0,1,0).");
                    normal = new Vector3f(0, 1, 0);
                } else {
                    FloatTuple normalTuple = triangulatedObj.getNormal(normalIndex);
                    normal = new Vector3f(normalTuple.getX(), normalTuple.getY(), normalTuple.getZ());
                }

                FloatTuple posTuple = triangulatedObj.getVertex(vertexIndex);
                Vector3f pos = new Vector3f(posTuple.getX(), posTuple.getY(), posTuple.getZ());

                Vector3f scaledPos = new Vector3f(pos.x * size, pos.y * size, pos.z * size);
                intermediateData.updateBounds(scaledPos);

                float wobbleValue = scaledPos.y * matInfo.wobbleFactor;

                sectionModelDataFloats.add(scaledPos.x);
                sectionModelDataFloats.add(scaledPos.y);
                sectionModelDataFloats.add(scaledPos.z);
                sectionModelDataFloats.add(wobbleValue);
                sectionModelDataFloats.add(normal.x);
                sectionModelDataFloats.add(normal.y);
                sectionModelDataFloats.add(normal.z);
                sectionModelDataFloats.add(matInfo.diffuseColor.x);
                sectionModelDataFloats.add(matInfo.diffuseColor.y);
                sectionModelDataFloats.add(matInfo.diffuseColor.z);

                sectionVertexCount++;
            }
        }

        if (sectionVertexCount > 0) {
            intermediateData.sections.add(new SectionData(sectionVertexCount, new Vector3f(matInfo.diffuseColor.x, matInfo.diffuseColor.y, matInfo.diffuseColor.z), matInfo.wobbleFactor, groupName));
            modelDataList.addAll(sectionModelDataFloats);
        }
    }
}