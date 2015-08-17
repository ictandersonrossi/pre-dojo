package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import match.observable.ProcessGameLog;
import match.scratchData.bean.MatchData;
import match.scratchData.bean.MatchPlayers;
import match.scratchData.service.MatchService;

@Component("app")
public class App 
{
    public static void main( String[] args ) throws Exception{
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        App app = (App)context.getBean("app");
        app.start(args);
    }
    
    private static String NEWLINE = "\r\n";
    
    @Autowired
    private ProcessGameLog service;
    
    @Autowired
    private MatchService match;
    
    public void start(String[] args) throws Exception{
    	if (args.length == 0){
    		System.out.println(" informe o caminho completo do arquivo de log como um parametro da aplicação");
    		return;
    	}
    	validatePath(args[0]);
    	processFile(args[0]);
    	buildReport();
    }
    
    private void processFile(String pathname) throws Exception {
    	System.out.println("Lendo o arquivo :"+pathname);
    	Reader rd = new FileReader(pathname);
    	BufferedReader buffer = new BufferedReader(rd);   
    	String  st;  
    	while(( st = buffer.readLine())!=null){  
    		service.processLine(st);
    	} 
    	buffer.close();
	}

    private void buildReport(){
    	List<MatchData> lista = match.getListMatchData();
    	StringBuilder sb = new StringBuilder();
    	
    	for (MatchData m : lista) {
    		
    		sb.append("----------------------------------------------------").append(NEWLINE);
    		sb.append("REPORT Match ").append(m.getId()).append(NEWLINE);
    		sb.append("----------------------------------------------------").append(NEWLINE);
    		
    		Set<MatchPlayers> players = m.getPlayers();
    		for (MatchPlayers p : players) {
    			sb.append("Name :").append(p.getName()).append(NEWLINE);
    			sb.append("Kills :").append(p.getKill()).append(NEWLINE);
    			sb.append("Die :").append(p.getDie()).append(NEWLINE);
    			sb.append("Streak :").append(p.getMaxStreak()).append(NEWLINE);
    			
    			if (p.getBestWeapon() != null) {
    				sb.append("Best weapon :").append(p.getBestWeapon().getName()).append(NEWLINE);
    			}
    			sb.append("Award :").append(p.isAward());
    			sb.append(NEWLINE).append("----------------------------------------------------").append(NEWLINE);
    		}
			
		}
    	
    	System.out.println(sb);
    }
    
    
	private void validatePath(String pathname) throws IOException {
		File file = new File(pathname);
		if  ( !(file.exists() && file.isFile()) ){
			throw new IOException("Arquivo não encontrado ou não é um arquivo válido");
		}
	}

}
