/*CREATED BY PROFESSOR*/
package cs452.hw1;

import java.util.*;
import java.util.Optional;

	class Path implements FoundPath {
	List<String> path = new ArrayList<>();
	int totalN = 0;
	int totalON = 0;

	@Override
	public List<String> getPath() {
		// TODO Auto-generated method stub
		return path;
	}

	@Override
	public Optional<Integer> getTotalCost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int totalNodes() {
		// TODO Auto-generated method stub
		return totalN;
	}

	@Override
	public int openNodes() {
		// TODO Auto-generated method stub
		return totalN;
	}

}
