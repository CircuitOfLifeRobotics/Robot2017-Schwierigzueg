package com.team3925.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

/**
 * <p>
 * This class is useful for teams who build multiple versions of their robots or
 * who make frequent electrical or CAN configuration changes. It allows for the
 * exact same program to be run on robots with different electrical
 * configurations. This is achieved by storing a configs file on a RoboRIO. If
 * two RoboRIOs have config files with different electrical configuration
 * values, the same program can allocate PWM components with different ports on
 * differing robots.
 * </p>
 * <p>
 * This class is a singleton. Call {@link #getInstance()} to create or get an
 * instance of RIOConfigs on the file located at {user home}/config.txt (this
 * should always be "/home/lvuser/config.txt"). Call
 * {@link #getInstance(String)} to create or get an instance of RIOConfigs on a
 * file with the given filepath. Call {@link #getInstance(File)} to create or
 * get an instance of RIOConfigs on a given file.
 * </p>
 * <p>
 * Call {@link #addConfig(String, String, boolean)} to put a new configuration
 * in the configs file. <br/>
 * Call {@link #getConfig(String, Object, Function)} to get a configuration from
 * the file with a specific type or add a new one if it doesn't exist. <br/>
 * Call {@link #getConfig(String, String)} to get a configuration from the file
 * or add a new one if it doesn't exist. <br/>
 * Call {@link #getConfig(String)} to get a configuration from the file. <br/>
 * Call {@link #resetConfigs()} to remove all configurations from the file.
 * </p>
 * <p>
 * The recommended way to implement this is as such: create an instance of
 * RIOConfigs only in RobotMap. Use a static initializer in RobotMap (run when
 * the RobotMap class gets loaded when the program starts before robotInit()) to
 * create a RIOConfigs instance. Use that instance to define every value in
 * RobotMap. If you were following WPILib procedure, it should be easy to port
 * your code over to this. Simply make all RobotMap constants static and final,
 * and use a static initializer. Here's what that might look like:<br/>
 * Before:
 * 
 * <pre>
 * public class RobotMap {
 * 	public static int PORT_DRIVE_LEFT_A = 0, PORT_DRIVE_LEFT_B = 1, PORT_DRIVE_RIGHT_A = 2, PORT_DRIVE_RIGHT_B = 3;
 * 	public static int PORT_SHOOTER = 4;
 * 	public static int PORT_LOADER = 5;
 * 
 * 	public static boolean POLARITY_SHOOTER = false;
 * 	public static boolean POLARITY_LOADER = true;
 * }
 * </pre>
 * 
 * After:
 * 
 * <pre>
 * public class RobotMap {
 * 	public static int PORT_DRIVE_LEFT_A, PORT_DRIVE_LEFT_B, PORT_DRIVE_RIGHT_A, PORT_DRIVE_RIGHT_B;
 * 	public static int PORT_SHOOTER;
 * 	public static int PORT_LOADER;
 * 
 * 	public static boolean POLARITY_SHOOTER;
 * 	public static boolean POLARITY_LOADER;
 * 
 * 	private static final RIOConfigs CONFIGS;
 * 
 * 	static {
 * 		CONFIGS = RIOConfigs.getInstance(System.getProperty("user.home") + "/preferences/Schwierigzeug_comp.txt");
 * 
 * 		PORT_DRIVE_LEFT_A = CONFIGS.getConfig("port_drive_left_a", 0, Integer::parseInt);
 * 		PORT_DRIVE_LEFT_B = CONFIGS.getConfig("port_drive_left_b", 1, Integer::parseInt);
 * 		PORT_DRIVE_RIGHT_A = CONFIGS.getConfig("port_drive_right_a", 2, Integer::parseInt);
 * 		PORT_DRIVE_RIGHT_B = CONFIGS.getConfig("port_drive_right_b", 3, Integer::parseInt);
 * 		PORT_SHOOTER = CONFIGS.getConfig("port_shooter_flywheel_a", 4, Integer::parseInt);
 * 		PORT_LOADER = CONFIGS.getConfig("port_shooter_loader", 5, Integer::parseInt);
 * 
 * 		POLARITY_SHOOTER = CONFIGS.getConfig("POLARITY_shooter_flywheel_a", false, Boolean::parseBoolean);
 * 		POLARITY_LOADER = CONFIGS.getConfig("POLARITY_shooter_loader", true, Boolean::parseBoolean);
 * 	}
 * }
 * </pre>
 * </p>
 * <p>
 * Once a configs file is created, it can be modified easily through SSH or FTP
 * because it is a text file. Note that once a RIOConfigs object is created with
 * a given File, that File is made unwritable, both to users and the program.
 * This prohibits a configs file from being modified while it is being read
 * from. Simply stop a program and it will become editable.
 * </p>
 * <p>
 * The format of a configs file is as such:
 * 
 * <pre>
 * >Configuration name:configuration value.
 * >Another name:an0ther_Value. This text will be ignored
 * >One_L&st name:somevalue.
 * </pre>
 * 
 * As you can see, names and values support any characters except '>', ':' and
 * '.' From a '>' character to the next ':' character, the name is read. From a
 * ':' character to the next '.' character, the value is read (as a String). It
 * is possible to store primitive types simply by making sure that the value is
 * parseable. Comments can be made anywhere outside of '>' '.' pairs.
 * </p>
 * <p>
 * All file parsing is done at object initialization. Re-parsing the text file
 * is not yet possible without restarting the program. Note that this class is a
 * singleton variant. There can only be one RIOConfigs object for each File. As
 * such, calling {@link #getInstance()} multiple times does not result in
 * reinitialization or new file parsing.
 * </p>
 * 
 * @author Adam Mendenhall
 *
 */
public class RIOConfigs {
	private File file;
	private HashMap<String, String> values;
	private StringBuilder fileFlaws;

	private String prefix;
	private char data;

	private static final char PREFIX_CHAR = '>', VALUE_CHAR = ':', VALUE_END_CHAR = '!';
	private static HashMap<File, RIOConfigs> instances;

	public static RIOConfigs getInstance() {
		return getInstance(System.getProperty("user.home") + "/configs.txt");
	}

	public static RIOConfigs getInstance(String filename) {
		return getInstance(new File(filename));
	}

	public static RIOConfigs getInstance(File file) {
		if (instances == null)
			instances = new HashMap<>();
		RIOConfigs instance;
		if ((instance = instances.get(file)) == null) {
			instance = new RIOConfigs(file);
			instances.put(file, instance);
		}
		return instance;
	}

	private RIOConfigs(File file) {
		this.file = file;
		
		fileFlaws = new StringBuilder();
		values = new HashMap<>();
		StringBuilder block = new StringBuilder();
		LinearStateMachine stateMachine = new LinearStateMachine(itr -> {
			switch (data) {
			case PREFIX_CHAR:
				// buffer is comment. Do nothing
				block.setLength(0);
				return true;
			case VALUE_CHAR:
			case VALUE_END_CHAR:
				fileFlaws.append("Prefix char expected at index " + itr + "\n");
			default:
				block.append(data);
				return false;
			}
		}, itr -> {
			switch (data) {
			case VALUE_CHAR:
				// buffer is prefix, store
				prefix = block.toString();
				block.setLength(0);
				return true;
			case VALUE_END_CHAR:
			case PREFIX_CHAR:
				fileFlaws.append("Value start char expected at index " + itr + "\n");
			default:
				block.append(data);
				return false;
			}
		}, itr -> {
			switch (data) {
			case VALUE_END_CHAR:
				// buffer is value, store
				values.put(prefix, block.toString());
				block.setLength(0);
				return true;
			case PREFIX_CHAR:
			case VALUE_CHAR:
				fileFlaws.append("Value end char expected at index " + itr + "\n");
			default:
				block.append(data);
				return false;
			}
		});

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			file.setWritable(false);
			file.createNewFile();
			while (true) {
				if (reader.ready()) {
					data = (char) reader.read();
					stateMachine.run();
				} else
					break;
			}
			file.setWritable(true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("RIO configs initialized with following flaws: " + fileFlaws);
	}

	/**
	 * <p>
	 * This method looks for a configuration with the given name.<br/>
	 * If it is present, it uses the provided function to turn the name's
	 * corresponding value (a string in the configs file) into whatever type is
	 * implied by the second argument.
	 * </p>
	 * 
	 * <p>
	 * The second argument is used if there is no configuration with the given
	 * name.<br/>
	 * If there is no configuration with the given name, a new configuration is
	 * added to the file with the given name and second argument converted to a
	 * string through the {@link Object.toString()} method. That new
	 * configuration will also be available from the {@link #getConfig()}
	 * methods.
	 * </p>
	 * 
	 * <p>
	 * The following is an example of retrieving a boolean configuration named
	 * "isInverted" from the configs file using a lambda expression as the third
	 * argument to simplify the code. See {@link Function} for information
	 * regarding {@link FunctionalInterface}s <br/>
	 * <code>
	 * boolean isMyMotorInverted = getConfig("isInverted", false, string -> Boolean.getBoolean(string));
	 * </code><br/>
	 * This code can be simplified further using method references: <br/>
	 * <code>
	 * boolean isMyMotorInverted = getConfig("isInverted", false, Boolean::parseBoolean);
	 * </code><br/>
	 * </p>
	 * 
	 * @param name
	 *            the name of the configuration to be retrieved
	 * @param ifAbsent
	 *            the value to be stored as a String if there is no
	 *            configuration with the given name
	 * @param stringToT
	 *            a function to convert a String to the type being returned
	 * @return the object created using the given function from the string
	 *         corresponding to the configuration with the given name or the
	 *         given object if the configuration does not exist
	 */
	public <T> T getConfigOrAdd(String name, T ifAbsent, Function<String, T> stringToT) {
		Optional<String> value = getConfigOrEmptyOptional(name);
		if (value.isPresent()) {
//			System.out.println("config "+name+" is present, with value "+value.get());
			return stringToT.apply(value.get());
		}
//		System.out.println("config "+name+" is NOT present");
		addConfig(name, ifAbsent.toString(), false);
		return ifAbsent;
	}

	public Boolean getConfigOrAdd(String name, Boolean ifAbsent) {
		return getConfigOrAdd(name, ifAbsent, Boolean::parseBoolean);
	}

	public Integer getConfigOrAdd(String name, Integer ifAbsent) {
		return getConfigOrAdd(name, ifAbsent, Integer::parseInt);
	}

	public Long getConfigOrAdd(String name, Long ifAbsent) {
		return getConfigOrAdd(name, ifAbsent, Long::parseLong);
	}

	public Double getConfigOrAdd(String name, Double ifAbsent) {
		return getConfigOrAdd(name, ifAbsent, Double::parseDouble);
	}

	public Short getConfigOrAdd(String name, Short ifAbsent) {
		return getConfigOrAdd(name, ifAbsent, Short::parseShort);
	}

	public Byte getConfigOrAdd(String name, Byte ifAbsent) {
		return getConfigOrAdd(name, ifAbsent, Byte::parseByte);
	}
	
	/**
	 * <p>
	 * This method looks for a configuration with the given name.<br/>
	 * If it is present, it is returned as a string (exactly as it appears in
	 * the file).
	 * </p>
	 * 
	 * <p>
	 * The second argument is used if there is no configuration with the given
	 * name.<br/>
	 * If there is no configuration with the given name, a new configuration is
	 * added to the file with the given name and value. That new configuration
	 * will also be available from the {@link #getConfig()} methods.
	 * </p>
	 * 
	 * @param name
	 *            the name of the configuration to be retrieved
	 * @param ifAbsent
	 *            the value to be stored as a String if there is no
	 *            configuration with the given name
	 * @return the corresponding value for the given configuration name or the
	 *         value provided by ifAbsent
	 */
	public String getConfigOrAdd(String name, String ifAbsent) {
		Optional<String> value = getConfigOrEmptyOptional(name);
		if (value.isPresent())
			return value.get();
		addConfig(name, ifAbsent, false);
		return ifAbsent;
	}

	/**
	 * <p>
	 * This method returns an {@link Optional} with either the configuration for
	 * the given name, or null inside it. If a configuration with the given name
	 * exists, then the {@link Optional} will have the value of that
	 * configuration inside it. If a configuration with the given name does not
	 * exist, then the {@link Optional} will have nothing in it (detected by
	 * {@link Optional.isPresent()}
	 * </p>
	 * 
	 * @param name
	 *            the name of the configuration to be retrieved
	 * @return an Optional containing nothing if the given configuration is not
	 *         present or the value corresponding to the configuration with the
	 *         given name
	 */
	public Optional<String> getConfigOrEmptyOptional(String name) {
		return Optional.ofNullable(values.get(name));
	}

	/**
	 * <p>
	 * Appends the given configuration (name and value pair) to the file If
	 * override is false, if a configuration with the same name does not already
	 * occur. If override is true, whether a configuration with the same name
	 * existed is irrelevant. Returns true if the write succeeded and (if
	 * override is false) if there was no value with the given name.
	 * </p>
	 * <p>
	 * The given configuration is available through the {@link #getConfig()}
	 * methods after this method is called.
	 * </p>
	 * 
	 * @param name
	 *            the name of the configuration to add
	 * @param value
	 *            the value of the configuration to add
	 * @param override
	 *            whether or not to overwrite a configuration if it already
	 *            exists with the given name
	 * @return true if the write to the file succeeded and (if override is
	 *         false) the configuration did not previously exist
	 */
	public boolean addConfig(String name, String value, boolean override) {
		if (!override && values.containsKey(name))
			return false;
		values.put(name, value);
		return appendToFile(name, value);
	}

	/**
	 * Clears the configs file and all configurations in this object. New
	 * configurations may be added after this.
	 * 
	 * @return true if the write to the file succeeded, false otherwise.
	 */
	public boolean resetConfigs() {
		try (FileWriter writer = new FileWriter(file)) {
			writer.write("");
			values.clear();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	protected boolean appendToFile(String prefix, String value) {
		try (FileWriter writer = new FileWriter(file, true)) {
			writer.append(PREFIX_CHAR);
			writer.append(prefix);
			writer.append(VALUE_CHAR);
			writer.append(value);
			writer.append(VALUE_END_CHAR);
			writer.append('\n');
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void reloadConfigs() {
		fileFlaws = new StringBuilder();
		values = new HashMap<>();
		StringBuilder block = new StringBuilder();
		LinearStateMachine stateMachine = new LinearStateMachine(itr -> {
			switch (data) {
			case PREFIX_CHAR:
				// buffer is comment. Do nothing
				block.setLength(0);
				return true;
			case VALUE_CHAR:
			case VALUE_END_CHAR:
				fileFlaws.append("Prefix char expected at index " + itr + "\n");
			default:
				block.append(data);
				return false;
			}
		}, itr -> {
			switch (data) {
			case VALUE_CHAR:
				// buffer is prefix, store
				prefix = block.toString();
				block.setLength(0);
				return true;
			case VALUE_END_CHAR:
			case PREFIX_CHAR:
				fileFlaws.append("Value start char expected at index " + itr + "\n");
			default:
				block.append(data);
				return false;
			}
		}, itr -> {
			switch (data) {
			case VALUE_END_CHAR:
				// buffer is value, store
				values.put(prefix, block.toString());
				block.setLength(0);
				return true;
			case PREFIX_CHAR:
			case VALUE_CHAR:
				fileFlaws.append("Value end char expected at index " + itr + "\n");
			default:
				block.append(data);
				return false;
			}
		});

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			file.setWritable(false);
			file.createNewFile();
			while (true) {
				if (reader.ready()) {
					data = (char) reader.read();
					stateMachine.run();
				} else
					break;
			}
			file.setWritable(true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("RIO configs has the flaws " + fileFlaws);
	}

	/**
	 * 
	 * This class accepts an array of functions. When {@link #execute()} is
	 * called, a function from the array is called with an integer storing the
	 * count of how many times execute has been called previously. If the return
	 * value from a function is true, the next time {@link #execute()} is
	 * called, the next function in the array is called. Once the end of the
	 * array is reached, it loops back to the beginning.
	 * 
	 * @author Adam Mendenhall
	 * 
	 */
	public class LinearStateMachine implements Runnable {

		private Function<Integer, Boolean>[] states;
		private int idx, itr;

		@SafeVarargs
		public LinearStateMachine(Function<Integer, Boolean>... states) {
			itr = idx = 0;
			this.states = states;
		}

		@Override
		public void run() {
			if (states[idx].apply(itr++)) {
				idx++;
				if (idx >= states.length)
					idx = 0;
			}
		}

	}

}
