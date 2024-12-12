package com.tcn.cosmoslibrary.common.network;

import java.util.function.Function;

import com.mojang.datafixers.util.Function9;

import net.minecraft.network.codec.StreamCodec;

public class StreamCodecHelper {

    public static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9> StreamCodec<B, C> composite(
        final StreamCodec<? super B, T1> codec1,
        final Function<C, T1> getter1,
        final StreamCodec<? super B, T2> codec2,
        final Function<C, T2> getter2,
        final StreamCodec<? super B, T3> codec3,
        final Function<C, T3> getter3,
        final StreamCodec<? super B, T4> codec4,
        final Function<C, T4> getter4,
        final StreamCodec<? super B, T5> codec5,
        final Function<C, T5> getter5,
        final StreamCodec<? super B, T6> codec6,
        final Function<C, T6> getter6,
        final StreamCodec<? super B, T7> codec7,
        final Function<C, T7> getter7,
        final StreamCodec<? super B, T8> codec8,
        final Function<C, T8> getter8,
        final StreamCodec<? super B, T9> codec9,
        final Function<C, T9> getter9,
        final Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, C> factory
    ) {
        return new StreamCodec<B, C>() {
            @Override
            public C decode(B b) {
                T1 t1 = codec1.decode(b);
                T2 t2 = codec2.decode(b);
                T3 t3 = codec3.decode(b);
                T4 t4 = codec4.decode(b);
                T5 t5 = codec5.decode(b);
                T6 t6 = codec6.decode(b);
                T7 t7 = codec7.decode(b);
                T8 t8 = codec8.decode(b);
                T9 t9 = codec9.decode(b);
                return factory.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9);
            }

            @Override
            public void encode(B b, C c) {
                codec1.encode(b, getter1.apply(c));
                codec2.encode(b, getter2.apply(c));
                codec3.encode(b, getter3.apply(c));
                codec4.encode(b, getter4.apply(c));
                codec5.encode(b, getter5.apply(c));
                codec6.encode(b, getter6.apply(c));
                codec7.encode(b, getter7.apply(c));
                codec8.encode(b, getter8.apply(c));
                codec9.encode(b, getter9.apply(c));
            }
        };
    }
}
