public class Lobby {
	private static int tam=10;
	private int esp;
	private int rankAdj;
	private int rank;


	public Lobby(int rank) {
		this.jog = 0;
		this.rank=rank;
		rank.Adj=-1;
	}

	public void insere(int rank) {
		if (this.rank!=rank && (rank=this.rank-1 || rank=this.rank+1)) rankAdj=rank;
		esp++;
	}

	public espera() {
		while(esp<tam) 
			wait();
		esp=0;
		notifyAll();
	}


}