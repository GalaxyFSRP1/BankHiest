package com.yankton.bankheist.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MoneyUtil {
    public static ItemStack createMoneyStack(int amount) {
        ItemStack money = new ItemStack(Material.GOLD_INGOT, 1);
        ItemMeta meta = money.getItemMeta();
        meta.setDisplayName("§6Money: $" + amount);
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.setUnbreakable(true);
        money.setItemMeta(meta);
        return money;
    }
}

