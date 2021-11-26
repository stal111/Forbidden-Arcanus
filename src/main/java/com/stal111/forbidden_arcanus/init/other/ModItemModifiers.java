package com.stal111.forbidden_arcanus.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.modifier.EternalModifier;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

/**
 * Mod Item Modifiers <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.other.ModItemModifiers
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-24
 */
public class ModItemModifiers {

    public static final DeferredRegister<ItemModifier> MODIFIERS = DeferredRegister.create(ItemModifier.class, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<EternalModifier> ETERNAL = MODIFIERS.register("eternal", EternalModifier::new);
    public static final RegistryObject<ItemModifier> FIERY = MODIFIERS.register("fiery", ItemModifier::new);
}
