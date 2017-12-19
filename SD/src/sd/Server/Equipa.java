package sd.Server;


public class Equipa {
	private int ranks[] = null;
        private int jog;

        public Equipa(int ranks[],int jog){
            this.ranks = ranks;
            this.jog = jog;
        }
        
        
        
	public void insere(User user){
                ranks = new int[5];
                ranks[jog]=user.getRank();
		jog++;
		try{
                  while(jog<5)
            		wait();
                  notifyAll();
                    escolhaHeroi();
                }catch(InterruptedException e){
                    System.out.println(e.getMessage());
                } 
	}

    //devolve a media de ranks da equipa
	public int rank() {
                ranks = new int[5];
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