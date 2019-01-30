package com.anar.industrialPrograms;

import com.anar.industrialPrograms.OC.DriverIC2SteamBoiler;
import com.anar.industrialPrograms.OC.DriverIC2TradeOMat;
import com.anar.industrialPrograms.OC.DriverIC2WindTurbine;

import li.cil.oc.api.Driver;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = IndustrialPrograms.ID, version = IndustrialPrograms.VER)
public class IndustrialPrograms {
	public static final String ID = "industrialprograms";
	public static final String VER = "1.0";
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {

	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) {
		if (Loader.isModLoaded(IDs.IC2) && Loader.isModLoaded(IDs.OC)) {
			Driver.add(new DriverIC2SteamBoiler());
			Driver.add(new DriverIC2WindTurbine());
			Driver.add(new DriverIC2TradeOMat());
		}
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		
	}
	
	public static class IDs {
		public static final String IC2 = "ic2";
		public static final String OC = "opencomputers";
	}
}
