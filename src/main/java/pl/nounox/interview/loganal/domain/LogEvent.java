package pl.nounox.interview.loganal.domain;

import java.io.Serializable;

public class LogEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the unique event identifier
	 */
	private String id;

	/**
	 * whether the event was started or finished (can have values "STARTED" or
	 * "FINISHED"
	 * 
	 * Every event has 2 entries in the file - one entry when the event was started
	 * and another when the event was finished. The entries in the file have no
	 * specific order (a finish event could occur before a start event for a given
	 * id)
	 */
	private LogEventState state;

	/**
	 * the timestamp of the event in milliseconds
	 */
	private long timestamp;

	private String type;

	private String host;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LogEventState getState() {
		return state;
	}

	public void setState(LogEventState state) {
		this.state = state;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogEvent other = (LogEvent) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (state != other.state)
			return false;
		if (timestamp != other.timestamp)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LogEvent [id=" + id + ", state=" + state + ", timestamp=" + timestamp + ", type=" + type + ", host="
				+ host + "]";
	}
}
