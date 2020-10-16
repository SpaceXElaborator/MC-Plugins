package com.terturl.KillChest.Arena.Hologram;

import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

public class ArmorStandCreator {
	
	private ArmorStand _as;
	private String _name;
	
	public ArmorStandCreator(String s, ArmorStand as) {
		this._as = as;
		_name = s;
		SetAsName(_name);
	}

	public void resetPosition() {
		_as.setHeadPose(new EulerAngle(0,0,0));
		_as.setBodyPose(new EulerAngle(0,0,0));
		_as.setLeftArmPose(new EulerAngle(0,0,0));
		_as.setRightArmPose(new EulerAngle(0,0,0));
		_as.setLeftLegPose(new EulerAngle(0,0,0));
		_as.setRightLegPose(new EulerAngle(0,0,0));
	}
	
	public void kill() {
		_as.setHealth(0);
	}
	
	public void SetAsName(String name) {
		resetPosition();
		isVisible(false);
		isBaseplateVisible(false);
		isArmsVisible(false);
		hasGravity(false);
		_as.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
		_as.setCustomNameVisible(true);
	}
	
	//////////////////////////////////////////
	//										//
	// Boolean Logic Methods				//
	//										//
	//////////////////////////////////////////
	public ArmorStandCreator isVisible(boolean b) {
		_as.setVisible(b);
		return this;
	}
	
	public ArmorStandCreator isArmsVisible(boolean b) {
		_as.setArms(b);
		return this;
	}
	
	public ArmorStandCreator isBaseplateVisible(boolean b) {
		_as.setBasePlate(b);
		return this;
	}
	
	public ArmorStandCreator isSmall(boolean b) {
		_as.setSmall(b);
		return this;
	}
	
	public ArmorStandCreator hasGravity(boolean b) {
		_as.setGravity(b);
		return this;
	}
	
	//////////////////////////////////////////
	//										//
	// Private Getter Methods				//
	//										//
	//////////////////////////////////////////
	
	public String getName() {
		return _name;
	}
	
	public ArmorStand getArmorStand() {
		return _as;
	}
	
}