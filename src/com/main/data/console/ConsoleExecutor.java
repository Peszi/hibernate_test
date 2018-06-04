package com.main.data.console;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleExecutor {

    private Map<String, Map<String, CommandData>> commandsMap;

    private Object targetClass;

    private String[] lineArgs;
    private int lineArgsCount;

    public ConsoleExecutor(Object targetClass) {
        this.targetClass = targetClass;
        this.setupCommands(targetClass.getClass());
        this.readCommandsLoop();
    }

    // Scan for Annotated Methods (Commands)
    private void setupCommands(Class targetClass) {
        this.commandsMap = new HashMap<>();
        for (Method method : targetClass.getMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                final Command command = method.getAnnotation(Command.class);
                if (this.commandsMap.containsKey(command.group())) {
                    this.commandsMap.get(command.group())
                            .put(method.getName().toLowerCase(), new CommandData(method));
                } else {
                    Map<String, CommandData> commandsGroup = new HashMap<>();
                    commandsGroup.put(method.getName().toLowerCase(), new CommandData(method));
                    this.commandsMap.put(command.group(), commandsGroup);
                }
            }
        }
    }

    private void readCommandsLoop() {
        String line;
        Scanner scanner = new Scanner(System.in);
        while ((line = scanner.nextLine()) != null) {
            if (!this.checkAndReadArgs(line))
                continue;
            this.executeCommand(this.lineArgs[0].toLowerCase(), this.lineArgs);
        }
    }

    private boolean checkAndReadArgs(String line) {
        this.lineArgs = line.split(" ");
        this.lineArgsCount = this.lineArgs.length;
        if (this.lineArgsCount > 0)
            return true;
        System.err.println("Incorrect argument!");
        return false;
    }

    // Execute Method
    private void executeCommand(String command, String... args) {
        boolean commandFound = false;
        if (command.equalsIgnoreCase("help")) {
            this.helpCommand();
        } else {
            for (Map.Entry<String, Map<String, CommandData>> commandsGroup : this.commandsMap.entrySet()) {
                if (commandsGroup.getValue().containsKey(command)) {
                    commandFound = true;
                    CommandData commandData = commandsGroup.getValue().get(command);
                    if (!commandData.execute(this.targetClass, args))
                        System.err.println(this.getInvalidArgsMessage(commandData));
                }
            }
            if (!commandFound)
                System.out.println("No command found!");
        }
    }

    private void helpCommand() {
        System.out.println("list of commands:");
        for (Map.Entry<String, Map<String, CommandData>> commandsGroup : this.commandsMap.entrySet()) {
            System.out.println(" GROUP: " + commandsGroup.getKey().toUpperCase());
            for (CommandData commandData : commandsGroup.getValue().values())
                System.out.println("  -" + commandData.getDescription());
        }
    }

    private String getInvalidArgsMessage(CommandData commandData) {
        return "invalid arguments - " + commandData.getArgumentsDescription();
    }
}
