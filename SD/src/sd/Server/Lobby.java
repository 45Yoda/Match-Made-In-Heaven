public class Lobby {
	private static int tam=10;
	private int jog;
	private int rankAdj;
	private int rank;
	private Equipa equipaA;
	private Equipa equipaB;


//*************************noutra classe:****************************+
	public ...() {
		for(int i=0;i<10;i++) 
			lobbys[i]= new lobby(i);
	}

	//distribuir um user de cada vez para nao haver mudanças de rankAdj
	//enquanto outra thread está a consultar
	public void distribuirUser(user) {
		lock.lock();
		try {
			int rank = user.getRank();
			int lobby=-1;
			lobbys[bestLobby(rank)].espera(user);
		}
		finally {lock.unlock();}
	}

	//devolve o lobby mais cheio onde o user pode jogar
	public int bestLobby(int rank) {
		lock.lock();
		try {
		if (rank==-1) return ocup(0,4);
		else {
			int min=-1;max=-1;
			if (rank==0) {
				if (lobbys[1].getRankAdj()==0) {
					min=rank;
					max=rank+1;
					return ocup(0,1);
				}
				else return 0;
			}
			else if (rank==9) {
				if (lobbys[8].getRankAdj()==9) {
					min=rank-1;
					max=rank;
					return ocup(8,9);
				}
				else return 9;
			}
			else {
				if (lobbys[rank-1].getRankAdj()==rank) min=rank-1;
					else min=rank;
				if (lobbys[rank+1].getRankAdj()==rank) min=rank+1;
					else max=rank;
				if (min==max) return rank;
				else return ocup(min,max);
				}
		}
	}
		finally {lock.unlock();}
	}

	//devolve o lobby mais cheio entre dois pontos
	public int ocup(int min,int max) {
		int m=-1;
		for (int i = min;i<=max;i++)
			if (lobbys[i].getJog()>m) m=i;
		return i;
	}
//*******************************************************************+
	public Lobby(int rank) {
		this.jog = 0;
		this.rank=rank;
		rank.Adj=-1;
		equipaA= new Equipa();
		equipaB = new Equipa();
	}


	//devolve numero de jogadores
	public int getJog() {return this.jog;}
	public int getRank() {return this.rank;}
	public int getRankAdj() {return this.rankAdj;}
	public espera(User user) {
		if ( user.getRank()!=-1 && this.rank!=user.getRank()) rankAdj=user.getRank();
			jog++;
		while(jog<tam) 
			wait();
		notifyAll();
		jog=0;
		if (equipaA.getJog()==5)
			equipaB.insere(user);
		else if (equipaB.getJog()==5)
			equipaA.insere(user);
		else if (equipaA.rank()>equipaB.rank) {
				if (user.getRank()>=equipaA.rank())
					equipaB.insere(user);
				else equipaA.insere(user);
		} else {
			if (user.getRank()>=equipaA.rank())
					equipaA.insere(user);
				else equipaB.insere(user);
		}
	}


}