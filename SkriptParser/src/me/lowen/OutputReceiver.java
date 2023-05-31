package me.lowen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import me.lowen.gui.SkUIFrame;

public class OutputReceiver implements Runnable {

	private File serverDirectory;
	private char ps = File.separatorChar;
	BufferedReader stdInput;
	BufferedWriter outPutter;
	volatile boolean startReading = false;
	volatile boolean done = false;
	volatile String parsedLines;
	SkUIFrame frame;
	

	public OutputReceiver(File serverDirectory) {
		this.serverDirectory = serverDirectory;
	}

	@Override
	public void run() {
		try {
		Runtime rt = Runtime.getRuntime();
		String hi = "java -Xms1024M -Xmx1024M -Dterminal.jline=false -Dterminal.ansi=true -XX:+UseG1GC -XX:+ParallelRefProcEnabled -XX:MaxGCPauseMillis=200 -XX:+UnlockExperimentalVMOptions -XX:+DisableExplicitGC -XX:+AlwaysPreTouch -XX:G1HeapWastePercent=5 -XX:G1MixedGCCountTarget=4 -XX:G1MixedGCLiveThresholdPercent=90 -XX:G1RSetUpdatingPauseTimePercent=5 -XX:SurvivorRatio=32 -XX:+PerfDisableSharedMem -XX:MaxTenuringThreshold=1 -XX:G1NewSizePercent=30 -XX:G1MaxNewSizePercent=40 -XX:G1HeapRegionSize=8M -XX:G1ReservePercent=20 -XX:InitiatingHeapOccupancyPercent=15 -Dusing.aikars.flags=https://mcflags.emc.gs -Daikars.new.flags=true -jar paper.jar nogui";
		Process proc = rt.exec(hi, new String[0], serverDirectory);

		stdInput = new BufferedReader(new 
		     InputStreamReader(proc.getInputStream()));

		BufferedReader stdError = new BufferedReader(new 
		     InputStreamReader(proc.getErrorStream()));
		outPutter = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream()));

		// Read the output from the command
		System.out.println("Here is the standard output of the command:\n");
		String s = null;
		boolean skriptOnce = false;
		while ((s = stdInput.readLine()) != null /*&& dontRead == false*/) {
			if (s.contains("Failed to start the minecraft server")) {
				if (frame != null)
				frame.getHeader().setIfInErroredState(true);
			}
			if (s.contains("Done (")) {
				if (frame != null)
				frame.getHeader().setIfInLoadingState(false);
			}
			if (startReading == true) {
				System.out.println("readingfs true");
				
				// I dont know anoymore man. This string of characters is the mose ridiculouis thing I've ever seen, but it's very nessacary
				if (s.contains("[m[38;2;170;170;170m[[m[38;2;255;170;0mSkript[m[38;2;170;170;170m]")) {
					System.out.println("Skript was found");
					if (skriptOnce == false) {
						System.out.println("was");
						skriptOnce = true;
					} else {
						parsedLines += s;
						skriptOnce = false;
						startReading = false;
					}
					
				}
				if (skriptOnce) {
					parsedLines += s + "\n";
				}
				
			}
		    System.out.println(s);
		}

		// Read any errors from the attempted command
		System.out.println("Server shut down");
		while ((s = stdError.readLine()) != null) {
		    System.out.println(s);
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendText(String text) {
		try {
			outPutter.write(text);
			outPutter.newLine();
			outPutter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String parseSkript(String input) {
		
		try {
		Files.writeString(Paths.get(serverDirectory.getAbsolutePath() + ps + "plugins" + ps + "Skript" + ps + "scripts" + ps + "parsedSkript.sk"),
				input,
				StandardCharsets.UTF_8);
//		dontRead = true;
		outPutter.write("sk reload parsedSkript");
		outPutter.newLine();
		outPutter.flush();
		startReading = true;
		// actually needed
		System.out.println("Starting to read...");
		while (startReading == true);
		System.out.println("finsihed reading!");
		String tempString = new String(parsedLines);
		parsedLines = "";
		return tempString;
			}
		
		 catch (IOException e) {
			 e.printStackTrace();
			 return "There was an error!";
		 }
			
		
		
	}
	
	public SkUIFrame getLinkedFrame() {
		return frame;
	}

	public void setLinkedFrame(SkUIFrame frame) {
		this.frame = frame;
	}
	
	public static String trimOutput(String output) {
		ArrayList<String> lines = new ArrayList<>(List.of(output.split("\\r?\\n")));
		lines.remove(0);
		lines.remove(lines.size() - 1);
		int size = lines.size();
		for (int i = 0; i < lines.size(); i++) {
			
			String theJim;
			String newLine = lines.get(i).replace("(parsedSkript.sk)", "");
			newLine = newLine.replaceAll("\u001B\\[[;\\d]*m", "");
			newLine = newLine.replaceFirst("\\[(.*?)\\]:", "");
			if (newLine.isBlank()) {
				lines.remove(i);
				i--;
				continue;
			}
			lines.set(i, newLine +  "\n");
			
		}
		// removing the 
//		lines.set(size - 1, lines.get(size - 1).substring(0, lines.get(size -1).length()));
		
		return SkUtils.turnArrayListIntoString(lines);
		
	}
	
	
	
}
