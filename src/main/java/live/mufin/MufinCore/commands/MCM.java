package live.mufin.MufinCore.commands;

public interface MCM {
  String commandName();
  String[] commandAliases();
  String usage();
  String description();
  String permission();
}
