package com.tianzunchina.sample.widget;

import java.io.Serializable;

public class GVItem implements Serializable {
	private static final long serialVersionUID = 1L;
	protected int id = 1;
	protected String title= "";
	protected String description= "";
	protected String attachPath = null;
	protected int resID = -1;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAttachPath() {
		return attachPath;
	}

	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}
	public int getResID() {
		return resID;
	}
	public void setResID(int resID) {
		this.resID = resID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		GVItem other = (GVItem) obj;
		if (id != other.id)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GVItem{" +
				"id=" + id +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", attachPath='" + attachPath + '\'' +
				", resID=" + resID +
				'}';
	}
}
