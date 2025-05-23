package de.rhm176.api.base.mixin;

import de.rhm176.api.base.duck.BinaryReaderDuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import utils.BinaryReader;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

@Mixin(BinaryReader.class)
public class BinaryReaderMixin implements BinaryReaderDuck {
    /**
     * @reason More performant and required for peeking (calling {@link InputStream#mark(int)}, reading a value,
     * and then calling {@link InputStream#reset()})
     */
    @ModifyArg(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/io/DataInputStream;<init>(Ljava/io/InputStream;)V"
            )
    )
    private InputStream wrapInBufferedStream(InputStream in) {
        return new BufferedInputStream(in);
    }

    @Shadow
    private DataInputStream reader;

    @Override
    public int silk_blueprints_api$peekInt() throws IOException {
        this.reader.mark(Integer.BYTES);
        int val = this.reader.readInt();
        this.reader.reset();

        return val;
    }
}
