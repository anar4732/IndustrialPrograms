package com.anar.industrialPrograms.OC;

import ic2.core.block.personal.TileEntityTradeOMat;
import li.cil.oc.api.Network;
import li.cil.oc.api.driver.NamedBlock;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.AbstractManagedEnvironment;
import li.cil.oc.api.prefab.DriverSidedTileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class DriverIC2TradeOMat extends DriverSidedTileEntity {
	@Override
	public Class<?> getTileEntityClass() {
		return TileEntityTradeOMat.class;
	}

	@Override
	public ManagedEnvironment createEnvironment(World world, BlockPos pos, EnumFacing side) {
		return new Environment((TileEntityTradeOMat) world.getTileEntity(pos));
	}

	public static final class Environment extends AbstractManagedEnvironment implements NamedBlock {
		private TileEntityTradeOMat TE;
		private int oldTradeValue = 0;

		public Environment(final TileEntityTradeOMat tileEntity) {
			setNode(Network.newNode(this, Visibility.Network).withComponent(preferredName(), Visibility.Network).create());
			TE = tileEntity;
		}

		@Override
		public String preferredName() {
			return "trade_o_mat";
		}

		@Override
		public int priority() {
			return 1;
		}
		
	    @Override
	    public boolean canUpdate() {
	        return true;
	    }

	    @Override
	    public void update() {
	    	if (oldTradeValue < TE.totalTradeCount) {
	    		ItemStack itemb = TE.demandSlot.get();
	    		ItemStack itema = TE.offerSlot.get();
	    		node().sendToReachable("computer.signal", "trade_performed", TE.totalTradeCount, Item.REGISTRY.getNameForObject(itema.getItem()).getNamespace()+":"+Item.REGISTRY.getNameForObject(itema.getItem()).getPath(), itema.getItemDamage(), itema.getCount(), Item.REGISTRY.getNameForObject(itemb.getItem()).getNamespace()+":"+Item.REGISTRY.getNameForObject(itemb.getItem()).getPath(), itemb.getItemDamage(), itemb.getCount());
	    		oldTradeValue = TE.totalTradeCount;
	    	}
	    }

	    @Override
	    public void onConnect(final Node node) {
	    	oldTradeValue = TE.totalTradeCount;
	    }

	    @Override
	    public void load(final NBTTagCompound nbt) {
	    	super.load(nbt);
	    	oldTradeValue = nbt.getInteger("oldTradeValue");
	    }

	    @Override
	    public void save(final NBTTagCompound nbt) {
	    	super.save(nbt);
	    	nbt.setInteger("oldTradeValue", oldTradeValue);
	    }
	    
		@Callback(doc = "function():int")
		public Object[] getStock(Context context, Arguments args) {
			return new Object[] {TE.stock};
		}
		
	}
}
