package com.stal111.forbidden_arcanus.block;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Set;

public class ModWoodType extends WoodType {

    private static final Set<ModWoodType> VALUES = new ObjectArraySet<>();
    public static final ModWoodType CHERRYWOOD = register(new ModWoodType("cherrywood"));
    public static final ModWoodType MYSTERYWOOD = register(new ModWoodType("mysterywood"));
    public static final ModWoodType EDELWOOD = register(new ModWoodType("edelwood"));
    private final String name;

    protected ModWoodType(String p_i225775_1_) {
        super(p_i225775_1_);
        this.name = p_i225775_1_;
    }

    private static ModWoodType register(ModWoodType p_227047_0_) {
        VALUES.add(p_227047_0_);
        return p_227047_0_;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String name() {
        return this.name;
    }
}
