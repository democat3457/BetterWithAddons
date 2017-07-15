package betterwithaddons.tileentity;

import betterwithaddons.block.ModBlocks;
import betterwithaddons.crafting.manager.CraftingManagerInfuserTransmutation;
import betterwithaddons.crafting.recipes.SmeltingRecipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class TileEntityInfuser extends TileEntityBase implements ITickable {
    @Override
    public void writeDataToNBT(NBTTagCompound compound) {

    }

    @Override
    public void readDataFromNBT(NBTTagCompound compound) {

    }

    public TileEntityAncestrySand getBottomSand() //TODO: Capability support? Addon addons?????
    {
        TileEntity te = world.getTileEntity(pos.down());
        if(te instanceof TileEntityAncestrySand)
            return (TileEntityAncestrySand) te;
        return null;
    }

    public boolean isValid()
    {
        return ModBlocks.infuser.isValid(world,pos);
    }

    public int getSpirits()
    {
        TileEntityAncestrySand sand = getBottomSand();
        if(sand != null)
            return sand.getSpirits();
        return 0;
    }

    public boolean consumeSpirits(int n)
    {
        TileEntityAncestrySand sand = getBottomSand();
        if(sand != null && sand.getSpirits() >= n) {
            sand.consumeSpirits(n);
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    @Override
    public void update() {
        if(world.isRemote)
            return;

        AxisAlignedBB aabb = new AxisAlignedBB(pos).offset(0,0.5,0);

        List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class,aabb);

        if(getSpirits() > 0)
        for (EntityItem item : items) {
            if(item.cannotPickup())
                continue;

            ItemStack stack = item.getEntityItem();
            SmeltingRecipe recipe = CraftingManagerInfuserTransmutation.instance().getSmeltingRecipe(stack);

            if(recipe != null)
            {
                ItemStack output = recipe.getOutput(stack);
                if(output.isEmpty())
                    continue;

                if(recipe.getInputCount() == stack.getCount())
                    stack = output;
                else {
                    EntityItem result = new EntityItem(world, item.posX, item.posY, item.posZ, output.copy());
                    result.setDefaultPickupDelay();
                    world.spawnEntity(result);
                    stack.shrink(recipe.getInputCount());
                }

                item.setEntityItemStack(stack);
                if(stack.isEmpty())
                    item.setDead();
                consumeSpirits(1);
                break;
            }
        }
    }
}