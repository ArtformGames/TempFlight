//package com.artformgames.plugin.tempflight.command;
//
//import cc.carm.lib.easysql.api.util.TimeDateUtils;
//import org.bukkit.Bukkit;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
//import org.bukkit.command.ConsoleCommandSender;
//import org.bukkit.entity.Player;
//import org.jetbrains.annotations.NotNull;
//
//public class TempFlightCommand implements CommandExecutor {
//
//    // /tempFlight <Player> <Time> [back]
//
//    @Override
//    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
//                             @NotNull String label, @NotNull String[] args) {
//        if (!PluginConfig.TEMP_FLY.ENABLE.getNotNull()) {
//            PluginMessages.DISABLED.send(sender);
//            return true;
//        }
//        if (!(sender instanceof ConsoleCommandSender)) {
//            sender.sendMessage("该指令只能在控制台使用。");
//            return true;
//        }
//
//        if (args.length < 2) return false;
//
//        Player target = Bukkit.getPlayer(args[0]);
//        if (target == null) {
//            sender.sendMessage("目标玩家不在线。");
//            return true;
//        }
//
//        long time = TimeStringUtils.toMilliSecPlus(args[1]);
//        if (time < 0) {
//            sender.sendMessage("时间格式错误。");
//            return true;
//        }
//
//        boolean back = true;
//        if (args.length > 2) {
//            if (args[2].equalsIgnoreCase("0") || args[2].equalsIgnoreCase("false")) {
//                back = false;
//            } else if (!args[2].equalsIgnoreCase("1") && !args[2].equalsIgnoreCase("true")) {
//                sender.sendMessage("参数错误，是否返回可选 true 或 false。");
//                return true;
//            }
//        }
//
//        TempFlyData data = UserHandler.get(target, TempFlyData.class);
//        if (data == null) {
//            sender.sendMessage("玩家暂未加载飞行时间数据，请稍后再试。");
//            return true;
//        }
//
//        if (data.isTempFlying()) {
//            sender.sendMessage("玩家正在进行临时飞行，无法再次操作。");
//            PluginMessages.FLY.FLYING.send(target, TimeDateUtils.toDHMSStyle(data.getRemainMillis() / 1000));
//            return true;
//        }
//
//        long cooldown = data.getCooldownMillis();
//        if (cooldown > 0) {
//            sender.sendMessage("玩家的临时飞行时间正在冷却中，请稍后再试。");
//            PluginMessages.FLY.COOLING.send(target, TimeDateUtils.toDHMSStyle(cooldown / 1000));
//            return true;
//        }
//
//        if (data.startFly(target, time, back ? target.getLocation() : null)) {
//            sender.sendMessage("成功为玩家启用为期 " + (time / 1000) + " 秒的临时飞行。");
//            PluginMessages.FLY.ENABLED.send(target, TimeDateUtils.toDHMSStyle(time / 1000));
//            if (back) PluginMessages.FLY.WILL_BACK.send(target);
//        } else {
//            sender.sendMessage("玩家正在进行临时飞行，无法再次操作。");
//        }
//        return true;
//    }
//
//}
