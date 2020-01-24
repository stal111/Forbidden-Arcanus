package com.stal111.forbidden_arcanus.block;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.block.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Set;

public class ModWoodType extends WoodType {

    private static final Set<ModWoodType> field_227044_g_ = new ObjectArraySet<>();
    public static final ModWoodType CHERRYWOOD = func_227047_a_(new ModWoodType("cherrywood"));
    public static final ModWoodType MYSTERYWOOD = func_227047_a_(new ModWoodType("mysterywood"));
    public static final ModWoodType EDELWOOD = func_227047_a_(new ModWoodType("edelwood"));
    private final String field_227045_h_;

    protected ModWoodType(String p_i225775_1_) {
        super(p_i225775_1_);
        this.field_227045_h_ = p_i225775_1_;
    }

    private static ModWoodType func_227047_a_(ModWoodType p_227047_0_) {
        field_227044_g_.add(p_227047_0_);
        return p_227047_0_;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return this.field_227045_h_;
    }
}
