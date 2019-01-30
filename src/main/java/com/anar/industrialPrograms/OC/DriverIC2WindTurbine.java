package com.anar.industrialPrograms.OC;

import ic2.core.block.kineticgenerator.tileentity.TileEntityWindKineticGenerator;
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

public final class DriverIC2WindTurbine extends DriverSidedTileEntity {
	@Override
	public Class<?> getTileEntityClass() {
		return TileEntityWindKineticGenerator.class;
	}

	@Override
	public ManagedEnvironment createEnvironment(World world, BlockPos pos, EnumFacing side) {
		return new Environment((TileEntityWindKineticGenerator) world.getTileEntity(pos));
	}

	public static final class Environment extends AbstractManagedEnvironment implements NamedBlock {
		private TileEntityWindKineticGenerator TE;

		public Environment(final TileEntityWindKineticGenerator tileEntity) {
			setNode(Network.newNode(this, Visibility.Network).withComponent(preferredName(), Visibility.Network).create());
			TE = tileEntity;
		}

		@Override
		public String preferredName() {
			return "wind_turbine";
		}

		@Override
		public int priority() {
			return 1;
		}
		
		@Callback(doc = "function():int")
		public Object[] getWindStrength(Context context, Arguments args) {
			return new Object[] {TE.getWindStrength()};
		}

		@Callback(doc = "function():int")
		public Object[] getKuOutput(Context context, Arguments args) {
			return new Object[] {TE.getKuOutput()};
		}
		
	}
}
