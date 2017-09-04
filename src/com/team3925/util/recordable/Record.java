package com.team3925.util.recordable;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.function.Function;

public class Record<T> extends LinkedHashMap<Double, T> {

	private transient static final long serialVersionUID = -3564277980640220534L;
	private transient T lastValue;
	public static final char BEGIN_TIME_MARKER = '>', BEGIN_DATA_MARKER = ':', END_DATA_MARKER = '!';

	public Record<T> clone() {
		Record<T> clone = new Record<>();
		clone.putAll(this);
		return clone;
	}

	@Override
	@Deprecated
	public T put(Double key, T value) {
		return super.put(key, value);
	}

	public boolean putIfNew(Double key, T value) {
		if (lastValue != null) {
			if (!lastValue.equals(value)) {
				super.put(key, value);
				lastValue = value;
				return true;
			}
		} else
			lastValue = value;
		return false;
	}

	public boolean save(File file) {
		return save(file, o -> o.toString());
	}

	public boolean save(File file, Function<T, String> conversion) {
		try (FileWriter writer = new FileWriter(file)) {
			writer.write("");
			for (double t : keySet()) {
				writer.append(BEGIN_TIME_MARKER);
				writer.append(Double.toString(t));
				writer.append(BEGIN_DATA_MARKER);
				writer.append(conversion.apply(get(t)));
				writer.append(END_DATA_MARKER);
				writer.append('\n');
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean saveWithSerialization(File file) {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try (FileWriter writer = new FileWriter(file)) {
			writer.write("");
			for (double t : keySet()) {
				writer.append(BEGIN_TIME_MARKER);
				writer.append(Double.toString(t));
				writer.append(BEGIN_DATA_MARKER);
				baos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(baos);
				oos.writeObject(get(t));
				oos.flush();
				writer.append(baos.toString());
				writer.append(END_DATA_MARKER);
				writer.append('\n');
			}
			if (oos != null)
				oos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Optional<Record<String>> recall(File file) {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			Record<String> out = new Record<>();
			String curr, time, value;
			int idxBeginTime, idxBeginData, idxEndData;
			while ((curr = reader.readLine()) != null) {
				idxBeginTime = curr.indexOf(BEGIN_TIME_MARKER);
				idxBeginData = curr.indexOf(BEGIN_DATA_MARKER);
				idxEndData = curr.indexOf(END_DATA_MARKER);
				if (idxBeginData >= 0 && idxBeginTime >= 0 && idxEndData >= 0) {
					time = curr.substring(idxBeginTime + 1, idxBeginData);
					value = curr.substring(idxBeginData + 1, idxEndData);
					out.put(Double.parseDouble(time), value);
				}
			}
			return Optional.of(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public static <T> Optional<Record<T>> recall(File file, Function<String, T> conversion) {
		Optional<Record<String>> record = recall(file);
		if (record.isPresent()) {
			Record<T> out = new Record<>();
			record.get().forEach((t, v) -> {
				out.put(t, conversion.apply(v));
			});
			return Optional.of(out);
		}
		return Optional.empty();
	}

	public static Optional<Record<Double>> recallDoubleRecord(File file) {
		return recall(file, Double::parseDouble);
	}

	public static Optional<Record<Integer>> recallIntegerRecord(File file) {
		return recall(file, Integer::parseInt);
	}

	public static Optional<Record<Boolean>> recallBooleanRecord(File file) {
		return recall(file, Boolean::parseBoolean);
	}

	public static Optional<Record<Long>> recallLongRecord(File file) {
		return recall(file, Long::parseLong);
	}

	public static Optional<Record<Short>> recallShortRecord(File file) {
		return recall(file, Short::parseShort);
	}

	public static Optional<Record<Byte>> recallByteRecord(File file) {
		return recall(file, Byte::parseByte);
	}

	public static Optional<Record<Object>> recallRecordWithSerialization(File file) {
		ByteArrayInputStream bais;
		ObjectInputStream ois = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			Record<Object> out = new Record<>();
			String curr, time, value;
			int idxBeginTime, idxBeginData, idxEndData;
			while ((curr = reader.readLine()) != null) {
				idxBeginTime = curr.indexOf(BEGIN_TIME_MARKER);
				idxBeginData = curr.indexOf(BEGIN_DATA_MARKER);
				idxEndData = curr.indexOf(END_DATA_MARKER);
				if (idxBeginData >= 0 && idxBeginTime >= 0 && idxEndData >= 0) {
					time = curr.substring(idxBeginTime + 1, idxBeginData);
					value = curr.substring(idxBeginData + 1, idxEndData);
					bais = new ByteArrayInputStream(value.getBytes());
					ois = new ObjectInputStream(bais);
					out.put(Double.parseDouble(time), ois.readObject());
				}
			}
			if (ois != null)
				ois.close();
			return Optional.of(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public static <R> Optional<Record<R>> recallRecordWithSerialization(File file, Function<Object, R> conversion) {
		ByteArrayInputStream bais;
		ObjectInputStream ois = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			Record<R> out = new Record<>();
			String curr, time, value;
			int idxBeginTime, idxBeginData, idxEndData;
			while ((curr = reader.readLine()) != null) {
				idxBeginTime = curr.indexOf(BEGIN_TIME_MARKER);
				idxBeginData = curr.indexOf(BEGIN_DATA_MARKER);
				idxEndData = curr.indexOf(END_DATA_MARKER);
				if (idxBeginData >= 0 && idxBeginTime >= 0 && idxEndData >= 0) {
					time = curr.substring(idxBeginTime + 1, idxBeginData);
					value = curr.substring(idxBeginData + 1, idxEndData);
					bais = new ByteArrayInputStream(value.getBytes());
					ois = new ObjectInputStream(bais);
					out.put(Double.parseDouble(time), conversion.apply(ois.readObject()));
				}
			}
			if (ois != null)
				ois.close();
			return Optional.of(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	// public static Record<String> recall(String filename) throws
	// ClassNotFoundException, IOException {
	// return recall(new File(filename));
	// }
	//
	// public static Record<String> recall(File file) {
	// FileInputStream fileIn = new FileInputStream(file);
	// ObjectInputStream objectIn = new ObjectInputStream(fileIn);
	// Object obj = objectIn.readObject();
	// objectIn.close();
	// fileIn.close();
	// return (Record<T>) obj;
	// }

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		forEach((t, v) -> {
			out.append(t);
			out.append(": ");
			out.append(v);
			out.append("!  ");
		});
		return out.toString();
	}

}
