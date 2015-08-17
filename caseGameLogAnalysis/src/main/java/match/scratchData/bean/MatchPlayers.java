package match.scratchData.bean;

import java.util.Date;
import java.util.Set;

public class MatchPlayers implements Comparable<MatchPlayers> {
	
	private String name;
	
	private int kill;
	
	private int die;
	
	private boolean award;
	
	private Set<KillWeapon> listKillWeapon;
	
	private int currentStreak;
	
	private int maxStreak;
	
	private Date awardDate;
	
	private int awardCount;
	
	private KillWeapon bestWeapon;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		MatchPlayers other = (MatchPlayers) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKill() {
		return kill;
	}

	public void setKill(int kill) {
		this.kill = kill;
	}

	public int getDie() {
		return die;
	}

	public void setDie(int die) {
		this.die = die;
	}

	public boolean isAward() {
		return award;
	}

	public void setAward(boolean award) {
		this.award = award;
	}

	public Set<KillWeapon> getListKillWeapon() {
		return listKillWeapon;
	}

	public void setListKillWeapon(Set<KillWeapon> listKillWeapon) {
		this.listKillWeapon = listKillWeapon;
	}

	public int compareTo(MatchPlayers o) {
		return name.compareTo(o.getName());
	}

	public int getCurrentStreak() {
		return currentStreak;
	}

	public void setCurrentStreak(int currentStreak) {
		this.currentStreak = currentStreak;
	}

	public int getMaxStreak() {
		return maxStreak;
	}

	public void setMaxStreak(int maxStreak) {
		this.maxStreak = maxStreak;
	}

	public Date getAwardDate() {
		return awardDate;
	}

	public void setAwardDate(Date awardDate) {
		this.awardDate = awardDate;
	}

	public int getAwardCount() {
		return awardCount;
	}

	public void setAwardCount(int awardCount) {
		this.awardCount = awardCount;
	}

	public KillWeapon getBestWeapon() {
		return bestWeapon;
	}

	public void setBestWeapon(KillWeapon bestWeapon) {
		this.bestWeapon = bestWeapon;
	}
	
}
