package com.anar.industrialPrograms.OC;

import ic2.core.block.machine.tileentity.TileEntitySteamGenerator;
import li.cil.oc.api.Network;
import li.cil.oc.api.driver.NamedBlock;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.AbstractManagedEnvironment;
import li.cil.oc.api.prefab.DriverSidedTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class DriverIC2SteamBoiler extends DriverSidedTileEntity {
	@Override
	public Class<?> getTileEntityClass() {
		return TileEntitySteamGenerator.class;
	}

	@Override
	public ManagedEnvironment createEnvironment(World world, BlockPos pos, EnumFacing side) {
		return new Environment((TileEntitySteamGenerator) world.getTileEntity(pos));
	}

	public static final class Environment extends AbstractManagedEnvironment implements NamedBlock {
		private TileEntitySteamGenerator TE;

		public Environment(final TileEntitySteamGenerator tileEntity) {
			setNode(Network.newNode(this, Visibility.Network).withComponent(preferredName(), Visibility.Network).create());
			TE = tileEntity;
		}

		@Override
		public String preferredName() {
			return "steam_boiler";
		}

		@Override
		public int priority() {
			return 1;
		}
		
		@Callback(doc = "function():int")
		public Object[] getSystemHeat(Context context, Arguments args) {
			return new Object[] {TE.getSystemHeat()};
		}

		@Callback(doc = "function():boolean")
		public Object[] isCalcified(Context context, Arguments args) {
			return new Object[] {TE.isCalcified()};
		}
		
		@Callback(doc = "function():int")
		public Object[] getCalcification(Context context, Arguments args) {
			return new Object[] {TE.getCalcification()};
		}
		
		@Callback(doc = "function():string")
		public Object[] getOutputType(Context context, Arguments args) {
			return new Object[] {TE.getOutputFluidName()};
		}
	
		@Callback(doc = "function():int")
		public Object[] getHeatInput(Context context, Arguments args) {
			return new Object[] {TE.getHeatInput()};
		}
	}
}
