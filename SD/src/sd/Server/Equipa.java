public class Equipa {
	private int[5] ranks;
	private int jog;

	public insere(user) {
		ranks[jog]=user.getRank();
		jog++;
		while(jog<5)
			wait()
		notifyAll();
		escolhaHeroi();
	}

    //devolve a media de ranks da equipa
	public int rank() {
		int soma=0;
		int jog=0;
		for(int i=0;i<this.jog;i++)
			if (ranks[i]!=-1) {
			soma+=ranks[i];
			jog++;
			}
		return soma/jog;
	}
}