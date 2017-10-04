package com.team3925.util.recordable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Optional;

public class Record extends LinkedHashMap<Double, String> {

	private transient static final long serialVersionUID = -3564277980640220534L;
	private transient String lastValue;
	public static final char BEGIN_TIME_MARKER = '>', BEGIN_DATA_MARKER = ':', END_DATA_MARKER = '!';
	private String name;

	public Record() {
		this(Long.toString(GregorianCalendar.getInstance().getTimeInMillis()));
	}

	public Record(String name) {
		this.name = name;
	}

	public Record clone() {
		Record clone = new Record();
		clone.putAll(this);
		return clone;
	}

	@Override
	@Deprecated
	public String put(Double key, String value) {
		return super.put(key, value);
	}

	public boolean putIfNew(Double key, String value) {
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
		try (FileWriter writer = new FileWriter(file)) {
			writer.write("");
			writer.append(name);
			writer.append('\n');
			for (double t : keySet()) {
				writer.append(BEGIN_TIME_MARKER);
				writer.append(Double.toString(t));
				writer.append(BEGIN_DATA_MARKER);
				writer.append(get(t));
				writer.append(END_DATA_MARKER);
				writer.append('\n');
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Optional<Record> recall(File file) {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String curr, time, value;
			int idxBeginTime, idxBeginData, idxEndData;
			curr = reader.readLine();
			Record out = new Record(curr);
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

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append(name);
		out.append(":: ");
		forEach((t, v) -> {
			out.append(t);
			out.append(": ");
			out.append(v);
			out.append("!  ");
		});
		return out.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
