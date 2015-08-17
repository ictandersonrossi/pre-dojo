package match.scratchData.bean;

public class KillWeapon implements Comparable<KillWeapon>{

	private String weaponName;
	
	private int count;

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((weaponName == null) ? 0 : weaponName.hashCode());
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
		KillWeapon other = (KillWeapon) obj;
		if (weaponName == null) {
			if (other.weaponName != null)
				return false;
		} else if (!weaponName.equals(other.weaponName))
			return false;
		return true;
	}

	public String getName() {
		return weaponName;
	}

	public void setName(String weaponName) {
		this.weaponName = weaponName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int compareTo(KillWeapon o) {
		return getName().compareTo(o.getName());
	}	

	
	
}
