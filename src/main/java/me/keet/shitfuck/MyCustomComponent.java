package me.keet.shitfuck;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public record MyCustomComponent(float temperature, boolean burnt) {
    public static final Codec<MyCustomComponent> CODEC = RecordCodecBuilder.create(builder -> {
        return builder.group(
                Codec.FLOAT.fieldOf("temperature").forGetter(MyCustomComponent::temperature),
                Codec.BOOL.optionalFieldOf("burnt", false).forGetter(MyCustomComponent::burnt)
        ).apply(builder, MyCustomComponent::new);
    });

    public static final ComponentType<MyCustomComponent> MY_CUSTOM_COMPONENT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(Shitfuck.MOD_ID, "custom"),
            ComponentType.<MyCustomComponent>builder().codec(MyCustomComponent.CODEC).build()
    );
}
