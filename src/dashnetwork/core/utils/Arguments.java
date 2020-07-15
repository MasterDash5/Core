package dashnetwork.core.utils;

import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.server.v1_16_R1.ArgumentEntity;
import net.minecraft.server.v1_16_R1.CommandDispatcher;
import net.minecraft.server.v1_16_R1.CommandListenerWrapper;

public class Arguments {

    public static ArgumentBuilder<CommandListenerWrapper, ?> literal(String argument) {
        return LiteralArgumentBuilder.literal(argument);
    }

    public static ArgumentBuilder<CommandListenerWrapper, ?> booleanType(String name) {
        return RequiredArgumentBuilder.argument(name, BoolArgumentType.bool());
    }

    public static ArgumentBuilder<CommandListenerWrapper, ?> doubleType(String name) {
        return RequiredArgumentBuilder.argument(name, DoubleArgumentType.doubleArg());
    }

    public static ArgumentBuilder<CommandListenerWrapper, ?> doubleType(String name, double min) {
        return RequiredArgumentBuilder.argument(name, DoubleArgumentType.doubleArg(min));
    }

    public static ArgumentBuilder<CommandListenerWrapper, ?> doubleType(String name, double min, double max) {
        return RequiredArgumentBuilder.argument(name, DoubleArgumentType.doubleArg(min, max));
    }

    public static ArgumentBuilder<CommandListenerWrapper, ?> floatType(String name) {
        return RequiredArgumentBuilder.argument(name, FloatArgumentType.floatArg());
    }

    public static ArgumentBuilder<CommandListenerWrapper, ?> floatType(String name, float min) {
        return RequiredArgumentBuilder.argument(name, FloatArgumentType.floatArg(min));
    }

    public static ArgumentBuilder<CommandListenerWrapper, ?> floatType(String name, float min, float max) {
        return RequiredArgumentBuilder.argument(name, FloatArgumentType.floatArg(min, max));
    }

    public static ArgumentBuilder<CommandListenerWrapper, ?> integerType(String name) {
        return RequiredArgumentBuilder.argument(name, IntegerArgumentType.integer());
    }

    public static ArgumentBuilder<CommandListenerWrapper, ?> integerType(String name, int min) {
        return RequiredArgumentBuilder.argument(name, IntegerArgumentType.integer(min));
    }

    public static ArgumentBuilder<CommandListenerWrapper, ?> integerType(String name, int min, int max) {
        return RequiredArgumentBuilder.argument(name, IntegerArgumentType.integer(min, max));
    }

    public static ArgumentBuilder<CommandListenerWrapper, ?> longType(String name) {
        return RequiredArgumentBuilder.argument(name, LongArgumentType.longArg());
    }

    public static ArgumentBuilder<CommandListenerWrapper, ?> longType(String name, long min) {
        return RequiredArgumentBuilder.argument(name, LongArgumentType.longArg(min));
    }

    public static ArgumentBuilder<CommandListenerWrapper, ?> longType(String name, long min, long max) {
        return RequiredArgumentBuilder.argument(name, LongArgumentType.longArg(min, max));
    }

    public static ArgumentBuilder<CommandListenerWrapper, ?> stringTypeQuote(String name) {
        return RequiredArgumentBuilder.argument(name, StringArgumentType.string());
    }

    public static ArgumentBuilder<CommandListenerWrapper, ?> stringTypeWord(String name) {
        return RequiredArgumentBuilder.argument(name, StringArgumentType.word());
    }

    public static ArgumentBuilder<CommandListenerWrapper, ?> stringTypeGreedy(String name) {
        return RequiredArgumentBuilder.argument(name, StringArgumentType.greedyString());
    }

    public static ArgumentBuilder<CommandListenerWrapper, ?> playersType(String name) {
        return CommandDispatcher.a(name, ArgumentEntity.d());
    }

    public static ArgumentBuilder<CommandListenerWrapper, ?> playerType(String name) {
        return CommandDispatcher.a(name, ArgumentEntity.c());
    }

    public static ArgumentBuilder<CommandListenerWrapper, ?> entitiesType(String name) {
        return CommandDispatcher.a(name, ArgumentEntity.multipleEntities());
    }

    public static ArgumentBuilder<CommandListenerWrapper, ?> entityType(String name) {
        return CommandDispatcher.a(name, ArgumentEntity.a());
    }

}
