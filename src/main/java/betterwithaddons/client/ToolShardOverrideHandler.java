package betterwithaddons.client;

import betterwithaddons.item.ModItems;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;

public class ToolShardOverrideHandler extends ItemOverrideList
{
    public static final ToolShardOverrideHandler INSTANCE = new ToolShardOverrideHandler();
    private HashMap<Item,IBakedModel> brokenModels = new HashMap<>();

    public ToolShardOverrideHandler() {
        super(ImmutableList.<ItemOverride>of());
    }

    public void addModel(Item item, IBakedModel model)
    {
        brokenModels.put(item,model);
    }

    @Override
    public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity)
    {
        if (!stack.isEmpty() && stack.hasCapability(ModItems.brokenArtifact.DATA_CAP,null))
        {
            ItemStack innerstack = stack.getCapability(ModItems.brokenArtifact.DATA_CAP,null).inner;
            Item item = innerstack.getItem();
            if(brokenModels.containsKey(item)) {
                return brokenModels.get(item);
            }
            else
            {
                ResourceLocation location = applyOverride(innerstack, world, entity);
                if (location != null) {
                    return net.minecraft.client.Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(net.minecraftforge.client.model.ModelLoader.getInventoryVariant(location.toString()));
                }
            }
        }
        return originalModel;
    }
}