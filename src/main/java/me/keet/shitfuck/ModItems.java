package me.keet.shitfuck;

import me.keet.shitfuck.item.LightningStick;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class ModItems {

    public static final Item SHITFUCK = register(new ShitFuckItem(new Item.Settings()), "shitfuck");
    public static final Item COUNTER = register(new CounterItem(new Item.Settings()), "counter");
    public static final Item LIGHTING_STICK = register(new LightningStick(new Item.Settings()), "lighting_stick");

    public static final Item GUIDITE_HELMET = register(new ArmorItem(ModArmorMaterials.GUIDITE, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(ModArmorMaterials.GUIDITE_DURABILITY_MULTIPLIER))), "guidite_helmet");
    public static final Item GUIDITE_CHESTPLATE = register(new ArmorItem(ModArmorMaterials.GUIDITE, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(ModArmorMaterials.GUIDITE_DURABILITY_MULTIPLIER))), "guidite_chestplate");
    public static final Item GUIDITE_LEGGINGS = register(new ArmorItem(ModArmorMaterials.GUIDITE, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(ModArmorMaterials.GUIDITE_DURABILITY_MULTIPLIER))), "guidite_leggings");
    public static final Item GUIDITE_BOOTS = register(new ArmorItem(ModArmorMaterials.GUIDITE, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(ModArmorMaterials.GUIDITE_DURABILITY_MULTIPLIER))), "guidite_boots");
    public static final Item GUIDITE_SWORD = register(new SwordItem(ShitFuckMaterial.INSTANCE, new Item.Settings()), "guidite_sword");

    public static final FoodComponent POISON_FOOD_COMPONENT = new FoodComponent.Builder()
            .alwaysEdible()
            // The duration is in ticks, 20 ticks = 1 second
            .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 6 * 20, 0), 1.0f)
            .build();

    public static final Item POISONOUS_APPLE = register(
            new Item(new Item.Settings().food(POISON_FOOD_COMPONENT)),
            "poisonous_apple"
    );

    public static final RegistryKey<ItemGroup> FREAKY_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(Shitfuck.MOD_ID, "item_group"));
    public static final ItemGroup FREAKY_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(SHITFUCK))
            .displayName(Text.translatable("itemGroup.freaky_group").formatted(Formatting.ITALIC))
            .build();

    public static Item register(Item item, String id) {
        // Create the identifier for the item.
        Identifier itemID = Identifier.of(Shitfuck.MOD_ID, id);

        // Return the registered item!
        return Registry.register(Registries.ITEM, itemID, item);
    }

    public static void registerEvents() {
        FuelRegistry.INSTANCE.add(SHITFUCK, 10 * 20);
        FuelRegistry.INSTANCE.add(COUNTER, 10 * 20);
        CompostingChanceRegistry.INSTANCE.add(SHITFUCK, 0.5f);
    }

    public static void registerGroup() {
        // Register the group.
        Registry.register(Registries.ITEM_GROUP, FREAKY_GROUP_KEY, FREAKY_GROUP);

        // Register items to the custom item group.
        ItemGroupEvents.modifyEntriesEvent(FREAKY_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(SHITFUCK);
            itemGroup.add(COUNTER);
            itemGroup.add(POISONOUS_APPLE);
            ///
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH)
                .register((itemGroup) -> itemGroup.add(SHITFUCK));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register((itemGroup) -> itemGroup.add(LIGHTING_STICK));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register((itemGroup) -> {
                    itemGroup.add(GUIDITE_SWORD);
                    itemGroup.add(GUIDITE_HELMET);
                    itemGroup.add(GUIDITE_CHESTPLATE);
                    itemGroup.add(GUIDITE_LEGGINGS);
                    itemGroup.add(GUIDITE_BOOTS);
                });
    }

    public static void initialize() {
        registerEvents();
        registerGroup();
    }
}

final class ShitFuckItem extends Item {

    public ShitFuckItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), 1.0F, 1.0F);
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}

final class CounterItem extends Item {

    public CounterItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        // Don't do anything on the client
        if (world.isClient()) {
            return TypedActionResult.success(stack);
        }

        // Read the current count and increase it by one
        int count = stack.getOrDefault(ModComponents.CLICK_COUNT_COMPONENT, 0);
        stack.set(ModComponents.CLICK_COUNT_COMPONENT, ++count);

        // Return the original stack
        return TypedActionResult.success(stack);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        int count = stack.getOrDefault(ModComponents.CLICK_COUNT_COMPONENT, 0);
        tooltip.add(Text.translatable("shitfuck.counter.info", count).formatted(Formatting.GOLD));
    }
}

