package de.rhm176.api.base;

import java.util.Objects;

public class Identifier implements Comparable<Identifier> {
    private final String namespace;
    private final String path;

    private Identifier(String namespace, String path) {
        assert isNamespaceValid(namespace);
        assert isPathValid(path);

        this.namespace = namespace;
        this.path = path;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getPath() {
        return path;
    }

    public static Identifier of(String namespace, String path) {
        return new Identifier(validateNamespace(namespace, path), validatePath(namespace, path));
    }

    private static String validatePath(String namespace, String path) {
        if (!isPathValid(path)) {
            throw new IllegalArgumentException("Non [a-z0-9/._-] character in path of identifier: " + namespace + ":" + path);
        } else {
            return path;
        }
    }
    private static String validateNamespace(String namespace, String path) {
        if (!isNamespaceValid(namespace)) {
            throw new IllegalArgumentException("Non [a-z0-9_.-] character in namespace of identifier: " + namespace + ":" + path);
        }
        return namespace;
    }

    public static boolean isNamespaceValid(String namespace) {
        for (int i = 0; i < namespace.length(); i++) {
            if (!isNamespaceCharacterValid(namespace.charAt(i))) {
                return false;
            }
        }

        return true;
    }
    private static boolean isNamespaceCharacterValid(char character) {
        return character == '_' || character == '-' || character >= 'a' && character <= 'z' || character >= '0' && character <= '9' || character == '.';
    }

    public static boolean isPathValid(String path) {
        for (int i = 0; i < path.length(); i++) {
            if (!isPathCharacterValid(path.charAt(i))) {
                return false;
            }
        }

        return true;
    }
    public static boolean isPathCharacterValid(char character) {
        return character == '_'
                || character == '-'
                || character >= 'a' && character <= 'z'
                || character >= '0' && character <= '9'
                || character == '/'
                || character == '.';
    }

    @Override
    public String toString() {
        return namespace + ":" + path;
    }

    @Override
    public int compareTo(Identifier identifier) {
        int i = this.path.compareTo(identifier.path);
        if (i == 0) {
            i = this.namespace.compareTo(identifier.namespace);
        }

        return i;
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, path);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else {
            return o instanceof Identifier identifier && this.namespace.equals(identifier.namespace) && this.path.equals(identifier.path);
        }
    }
}
