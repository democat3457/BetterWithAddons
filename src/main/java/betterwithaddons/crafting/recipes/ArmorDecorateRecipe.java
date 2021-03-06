package betterwithaddons.crafting.recipes;

import betterwithaddons.item.ItemSamuraiArmor;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nonnull;

public class ArmorDecorateRecipe extends ShapedOreRecipe
{
    ItemStack outputItem;

    public ArmorDecorateRecipe(ResourceLocation group, @Nonnull ItemStack result, Object... recipe) {
        super(group,result, recipe);
        outputItem = result;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    /*public boolean matches(InventoryCrafting inv, World worldIn)
    {
        int washiCount = 0;
        int goldCount = 0;
        int armorCount = 0;
        List<ItemStack> list = Lists.<ItemStack>newArrayList();

        for (int i = 0; i < inv.getSizeInventory(); ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);

            if (itemstack != null)
            {
                if (matchItem(itemstack,"nuggetGold"))
                {
                    goldCount++;
                }
                else if (matchItem(itemstack, ModItems.materialJapan.getMaterial("washi")))
                {
                    washiCount++;
                }
                else if (matchItem(itemstack,inputItem))
                {
                    armorCount++;
                }
                else if(isDye(itemstack))
                {
                    list.add(itemstack);
                }
            }
        }

        return list.size() == 2 && washiCount == 3 && goldCount == 3 && armorCount == 1;
    }*/

    private boolean isDye(ItemStack in)
    {
        return in.getItem() instanceof ItemDye;
    }

    /*private boolean matchItem(ItemStack in,String oreName)
    {
        for (ItemStack ore: OreDictionary.getOres(oreName)) {
            if(in.isItemEqual(ore))
                return true;
        }

        return false;
    }

    private boolean matchItem(ItemStack in, ItemStack match)
    {
        return in.isItemEqual(match);
    }*/

    /**
     * Returns an Item that is the result of this recipe
     */
    /*@Nullable
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        int[] aint = new int[3];
        int i = 0;
        int dyeCount = 0;
        int washiCount = 0;
        int goldCount = 0;
        int armorCount = 0;

        for (int k = 0; k < inv.getSizeInventory(); ++k)
        {
            ItemStack itemstack = inv.getStackInSlot(k);

            if (!itemstack.isEmpty()) {
                if (matchItem(itemstack, "nuggetGold")) {
                    goldCount++;
                } else if (matchItem(itemstack, ModItems.materialJapan.getMaterial("washi"))) {
                    washiCount++;
                } else if (matchItem(itemstack, inputItem)) {
                    armorCount++;
                } else if (isDye(itemstack)) {
                    float[] afloat = EntitySheep.getDyeRgb(EnumDyeColor.byDyeDamage(itemstack.getMetadata()));
                    int l1 = (int) (afloat[0] * 255.0F);
                    int i2 = (int) (afloat[1] * 255.0F);
                    int j2 = (int) (afloat[2] * 255.0F);
                    i += Math.max(l1, Math.max(i2, j2));
                    aint[0] += l1;
                    aint[1] += i2;
                    aint[2] += j2;
                    ++dyeCount;
                }
            }
        }

        if(dyeCount == 2 && washiCount == 3 && goldCount == 3 && armorCount == 1)
        {
            ItemSamuraiArmor itemarmor = (ItemSamuraiArmor)outputItem.getItem();
            int i1 = aint[0] / dyeCount;
            int j1 = aint[1] / dyeCount;
            int k1 = aint[2] / dyeCount;
            float f3 = (float)i / (float)dyeCount;
            float f4 = (float)Math.max(i1, Math.max(j1, k1));
            i1 = (int)((float)i1 * f3 / f4);
            j1 = (int)((float)j1 * f3 / f4);
            k1 = (int)((float)k1 * f3 / f4);
            int lvt_12_3_ = (i1 << 8) + j1;
            lvt_12_3_ = (lvt_12_3_ << 8) + k1;
            ItemStack outstack = outputItem.copy();
            itemarmor.setColor(outstack, lvt_12_3_);
            return outstack;
        }

        return ItemStack.EMPTY;
    }*/

    /**
     * Returns the size of the recipe area
     */
    /*public int getRecipeSize()
    {
        return 10;
    }

    @Nullable
    public ItemStack getRecipeOutput()
    {
        return ItemStack.EMPTY;
    }

    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
    {
        NonNullList<ItemStack> aitemstack = NonNullList.withSize(inv.getSizeInventory(),ItemStack.EMPTY);

        for (int i = 0; i < aitemstack.size(); ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);
            aitemstack.set(i,net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
        }

        return aitemstack;
    }*/

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        int dyeCount = 0;
        int[] aint = new int[3];
        int i = 0;

        for (int k = 0; k < inv.getSizeInventory(); ++k) {
            ItemStack itemstack = inv.getStackInSlot(k);

            if (!itemstack.isEmpty()) {
                if (isDye(itemstack)) {
                    float[] afloat = EntitySheep.getDyeRgb(EnumDyeColor.byDyeDamage(itemstack.getMetadata()));
                    int l1 = (int) (afloat[0] * 255.0F);
                    int i2 = (int) (afloat[1] * 255.0F);
                    int j2 = (int) (afloat[2] * 255.0F);
                    i += Math.max(l1, Math.max(i2, j2));
                    aint[0] += l1;
                    aint[1] += i2;
                    aint[2] += j2;
                    ++dyeCount;
                }
            }
        }

        ItemSamuraiArmor itemarmor = (ItemSamuraiArmor)outputItem.getItem();
        int i1 = aint[0] / dyeCount;
        int j1 = aint[1] / dyeCount;
        int k1 = aint[2] / dyeCount;
        float f3 = (float)i / (float)dyeCount;
        float f4 = (float)Math.max(i1, Math.max(j1, k1));
        i1 = (int)((float)i1 * f3 / f4);
        j1 = (int)((float)j1 * f3 / f4);
        k1 = (int)((float)k1 * f3 / f4);
        int lvt_12_3_ = (i1 << 8) + j1;
        lvt_12_3_ = (lvt_12_3_ << 8) + k1;
        ItemStack outstack = outputItem.copy();
        itemarmor.setColor(outstack, lvt_12_3_);
        return outstack;
    }
}
