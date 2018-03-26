package betterwithaddons.interaction.minetweaker;

import betterwithaddons.block.EriottoMod.BlockNettedScreen;
import betterwithaddons.crafting.manager.CraftingManagerWaterNet;
import betterwithaddons.crafting.recipes.NetRecipe;
import betterwithaddons.util.IngredientCraftTweaker;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.CraftTweaker;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@ZenRegister
@ZenClass(WaterNet.clazz)
public class WaterNet {
    public static final String clazz = "mods.betterwithaddons.WaterNet";

    @ZenMethod
    public static void add(IItemStack[] outputs, @NotNull IIngredient input) {
        NetRecipe r = new NetRecipe(BlockNettedScreen.SifterType.WATER, new IngredientCraftTweaker(input), 0, CraftTweakerMC.getItemStacks(outputs));
        CraftTweaker.LATE_ACTIONS.add(new Add(r));
    }

    @ZenMethod
    public static void remove(IItemStack input)
    {
        List<NetRecipe> recipes = CraftingManagerWaterNet.getInstance().findRecipeForRemoval(CraftTweakerMC.getItemStack(input));
        CraftTweaker.LATE_ACTIONS.add(new Remove(recipes));
    }

    public static class Add implements IAction
    {
        NetRecipe recipe;

        public Add(NetRecipe recipe) {
            this.recipe = recipe;
        }

        @Override
        public void apply() {
            CraftingManagerWaterNet.getInstance().addRecipe(recipe);
        }

        @Override
        public String describe() {
            return "Adding Water Net recipe:"+recipe.toString();
        }
    }

    public static class Remove implements IAction
    {
        List<NetRecipe> recipes;

        public Remove(List<NetRecipe> recipes) {
            this.recipes = recipes;
        }

        @Override
        public void apply() {
            CraftingManagerWaterNet.getInstance().getRecipes().removeAll(recipes);
        }

        @Override
        public String describe() {
            return "Removing "+recipes.size()+" Water Net recipes";
        }
    }
}
