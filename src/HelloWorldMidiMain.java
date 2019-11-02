/*
 * c2017-2019 Courtney Brown 
 * 
 * Class: H
 * Description: Demonstration of MIDI file manipulations, etc. & 'MelodyPlayer' sequencer
 * 
 */

import processing.core.*;

import java.util.*; 

//importing the JMusic stuff
import jm.music.data.*;
import jm.JMC;
import jm.util.*;
import jm.midi.*;

import java.io.UnsupportedEncodingException;
import java.net.*;

//import javax.sound.midi.*;

			//make sure this class name matches your file name, if not fix.
public class HelloWorldMidiMain extends PApplet {

	MelodyPlayer player; //play a midi sequence
	MidiFileToNotes midiNotes; //read a midi file
	ProbabilityGenerator<Integer> predictPitch = new ProbabilityGenerator<Integer>(); //added by Cameron Allen, declares a PredictMelody object of type integer in order to access the functions of the predictMelody class
	ProbabilityGenerator<Double> predictRhythm = new ProbabilityGenerator<Double>(); //added by Cameron Allen, declares a PredictMelody object of type double in order to access the functions of the predictMelody class
	MarkovGenerator<Integer> predictP = new MarkovGenerator<Integer>();
	MarkovGenerator<Double> predictR = new MarkovGenerator<Double>();
	
	float lastTimeKeyDown;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("HelloWorldMidiMain"); //change this to match above class & file name 		System.out.println(midiNotes.getPitchArray());		System.out.println(midiNotes.getPitchArray());
	}

	//setting the window size to 300x300
	public void settings() {
		size(500, 500);
	}

	//doing all the setup stuff
	public void setup() {
		fill(120, 50, 240);

		// returns a url
		String filePath = getPath("mid/MaryHadALittleLamb.mid");
		//playMidiFile(filePath);

		midiNotes = new MidiFileToNotes(filePath); //creates a new MidiFileToNotes -- reminder -- ALL objects in Java must 
													//be created with "new". Note how every object is a pointer or reference. Every. single. one.


//		// which line to read in --> this object only reads one line (or ie, voice or ie, one instrument)'s worth of data from the file
		midiNotes.setWhichLine(0);
		
		//code added by Cameron Allen
//		ArrayList<Integer> pitch; 
//		ArrayList<Double> rhythm;
//		pitch = midiNotes.getPitchArray(); //assigns all of the note pitches from the midi file to the ArrayList pitch		
//		rhythm = midiNotes.getRhythmArray(); //assigns all of the note rhythms from the midi file to the ArrayList rhythm
		
		//predictP.train(pitch);
		//predictR.train(rhythm);
//		predictPitch.train(pitch); //prints pitches with original probabilities
//		predictPitch.printAll();
//		predictPitch.generateMelody();
//		
//		ArrayList<Integer> newMelody = predictPitch.generate(20);
		
//		predictRhythm.train(rhythm); //prints rhythms with original probabilities
//		predictRhythm.printAll();
//		predictRhythm.generateMelody();
//		
//		midiNotes.SetPitches(predictPitch.GetToken());
//		midiNotes.SetRhythms(predictRhythm.GetToken());
		//end of added code 
		
		player = new MelodyPlayer(this, 100.0f);

		player.setup();
		//player.setMelody(newMelody);
		player.setRhythm(midiNotes.getRhythmArray());
	}

	public void draw() {
		player.play(); //play each note in the sequence -- the player will determine whether is time for a note onset
		textSize(20);
		text("Project 1\nUnitTest 1   '1'\nUnitTest 2   '2'\nUnitTest 3   '3'\n"
				+ "\nProject 2 \nUnitTest   '4'\n"
				+ "\nProject 3 \nUnitTest 1   '5'\n"
				+ "\nProject 5 \nUnitTests   '6'\n"
				+ "\nTest\n		'7'", 10, 30);
	}

	//this finds the absolute path of a file
	String getPath(String path) {

		String filePath = "";
		try {
			filePath = URLDecoder.decode(getClass().getResource(path).getPath(), "UTF-8");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath;
	}

	//this function is not currently called. you may call this from setup() if you want to test
	//this just plays the midi file -- all of it via your software synth. You will not use this function in upcoming projects
	//but it could be a good debug tool.
	void playMidiFile(String filename) {
		Score theScore = new Score("Temporary score");
		Read.midi(theScore, filename);
		Play.midi(theScore);
	}

	//this starts & restarts the melody.
	public void keyPressed() {
		if(!keyPressed) return;
		
//		if (key == 'p') {
//			player.reset();
//			//println("Melody started!");
//			
//		}
		else if (key == '4') {
			MarkovGenerator<Integer> prePitch = new MarkovGenerator<Integer>(); //added by Cameron Allen, declares a PredictMelody object of type integer in order to access the functions of the predictMelody class
			MarkovGenerator<Double> preRhythm = new MarkovGenerator<Double>();
			ProbabilityGenerator<Integer> preP = new ProbabilityGenerator<Integer>(); //added by Cameron Allen, declares a PredictMelody object of type integer in order to access the functions of the predictMelody class
			ProbabilityGenerator<Double> preR = new ProbabilityGenerator<Double>();
			
			ArrayList<Integer> pitch; 
			ArrayList<Double> rhythm;
			pitch = midiNotes.getPitchArray(); //assigns all of the note pitches from the midi file to the ArrayList pitch		
			rhythm = midiNotes.getRhythmArray();
			
			preP.train(pitch);
			preR.train(rhythm);
			prePitch.train(pitch);
			System.out.println("");
			preRhythm.train(rhythm);
			System.out.println("");
			
			ArrayList<Integer> newMelody = preP.generate(20);
			ArrayList<Double> newRhythm = preR.generate(20);
			
			
			System.out.println("After 10000 iterations:");
			prePitch.train(newMelody);
			System.out.println("\nAfter 10000 iterations:");
			preRhythm.train(newRhythm);
		}
		else if (key == '5') {
			MarkovGeneratorOrderN<Integer> prePitch = new MarkovGeneratorOrderN<Integer>(); //added by Cameron Allen, declares a PredictMelody object of type integer in order to access the functions of the predictMelody class
			MarkovGeneratorOrderN<Double> preRhythm = new MarkovGeneratorOrderN<Double>();
			
			ArrayList<Integer> pitch; 
			ArrayList<Double> rhythm;
			pitch = midiNotes.getPitchArray(); //assigns all of the note pitches from the midi file to the ArrayList pitch		
			rhythm = midiNotes.getRhythmArray();
			
			//System.out.println(pitch);
			for (int i = 1; i <= 10; i++) { //prints pitch transitionTable from orderN values 1 to 10
				prePitch.SetOrder(i);
				prePitch.train(pitch);
				prePitch.PrintProb();
			}
			
			for (int n = 1; n <= 10; n++) { //prints rhythm transitionTable from orderN values 1 to 10
				preRhythm.SetOrder(n);
				preRhythm.train(rhythm);
				preRhythm.PrintProb();
			}
			
			
		}
		else if (key == '1') {
			ProbabilityGenerator<Integer> prePitch = new ProbabilityGenerator<Integer>(); //added by Cameron Allen, declares a PredictMelody object of type integer in order to access the functions of the predictMelody class
			ProbabilityGenerator<Double> preRhythm = new ProbabilityGenerator<Double>();
			
			ArrayList<Integer> pitch; 
			ArrayList<Double> rhythm;
			pitch = midiNotes.getPitchArray(); //assigns all of the note pitches from the midi file to the ArrayList pitch		
			rhythm = midiNotes.getRhythmArray();
			prePitch.train(pitch);
			preRhythm.train(rhythm);
			prePitch.printAll();
			preRhythm.printAll();
			
		}
		else if (key == '2') {
			ProbabilityGenerator<Integer> prePitch = new ProbabilityGenerator<Integer>(); //added by Cameron Allen, declares a PredictMelody object of type integer in order to access the functions of the predictMelody class
			ProbabilityGenerator<Double> preRhythm = new ProbabilityGenerator<Double>();
			ArrayList<Integer> pitch; 
			ArrayList<Double> rhythm;
			pitch = midiNotes.getPitchArray(); //assigns all of the note pitches from the midi file to the ArrayList pitch		
			rhythm = midiNotes.getRhythmArray();
			prePitch.train(pitch);
			preRhythm.train(rhythm);
			
			ArrayList<Integer> newMelody = prePitch.generate(20);
			ArrayList<Double> newRhythm = preRhythm.generate(20);
			
			
			prePitch.printMelody(newMelody);
			preRhythm.printMelody(newRhythm);
		
			
		}
		else if (key == '3') {
			ProbabilityGenerator<Integer> prePitch = new ProbabilityGenerator<Integer>(); //added by Cameron Allen, declares a PredictMelody object of type integer in order to access the functions of the predictMelody class
			ProbabilityGenerator<Double> preRhythm = new ProbabilityGenerator<Double>();
			ArrayList<Integer> pitch; 
			ArrayList<Double> rhythm;
			pitch = midiNotes.getPitchArray(); //assigns all of the note pitches from the midi file to the ArrayList pitch		
			rhythm = midiNotes.getRhythmArray();
			prePitch.train(pitch); //prints pitches with original probabilities
			prePitch.generateMelody();
		
			
			preRhythm.train(rhythm); //prints rhythms with original probabilities
			preRhythm.generateMelody();
		}
		else if (key == '6') {
			Tree<Integer> prePitch = new Tree<Integer>(); //creates tree objects
			Tree<String> first = new Tree<String>();
			Tree<String> second = new Tree<String>();
			Tree<String> third = new Tree<String>();
			System.out.println("");
			
			
			String s1 = "abracadabra"; 
			String[] str1 = s1.split("", s1.length());
			ArrayList<String> string1 = new ArrayList<String>();
			for (int i = 0; i < str1.length; i++) { //makes given string into an arraylist of strings
				string1.add(str1[i]);
			}
			first.train(string1); //trains string
			System.out.println("------------------------------\nabracadabra: PST L=3 Pmin=" + first.getPMin() + "\n------------------------------");
			first.print(); //prints string
			System.out.println("\n");
			
			
			String s2 = "acadaacbda";
			String[] str2 = s2.split("", s2.length());
			ArrayList<String> string2 = new ArrayList<String>();
			for (int i = 0; i < str2.length; i++) { //makes given string into an arraylist of strings
				string2.add(str2[i]);
			}
			second.train(string2); //trains string
			System.out.println("------------------------------\nacadaacbda: PST L=3 Pmin=" + first.getPMin() + "\n------------------------------");
			second.print(); //prints string
			System.out.println("\n");
			
			
			String s3 = "abcccdaadcdaabcadad";
			String[] str3 = s3.split("", s3.length());
			ArrayList<String> string3 = new ArrayList<String>();
			for (int i = 0; i < str3.length; i++) { //makes given string into an arraylist of strings
				string3.add(str3[i]);
			}
			third.train(string3); //trains string
			System.out.println("------------------------------\nabcccdaadcdaabcadad: PST L=3 Pmin=" + first.getPMin() + "\n------------------------------");
			third.print(); //prints string
			System.out.println("\n");
		
			
			
			ArrayList<Integer> pitch; 
			pitch = midiNotes.getPitchArray(); //assigns all of the note pitches from the midi file to the ArrayList pitch	
			prePitch.train(pitch);
			
			System.out.println("------------------------------\nMary Had a Little Lamb: PST L=3 Pmin=" + first.getPMin() + "\n------------------------------");
			prePitch.print();
			
			Tree<Integer> prePitch2 = new Tree<Integer>();
			Tree<String> fourth = new Tree<String>();
			Tree<String> fifth = new Tree<String>();
			Tree<String> sixth = new Tree<String>();
			prePitch2.setPMin(0.15);
			fourth.setPMin(0.15);
			fifth.setPMin(0.15);
			sixth.setPMin(0.15);
			System.out.println("");
			
			
			String s4 = "abracadabra"; 
			String[] str4 = s4.split("", s4.length());
			ArrayList<String> string4 = new ArrayList<String>();
			for (int i = 0; i < str4.length; i++) { //makes given string into an arraylist of strings
				string4.add(str4[i]);
			}
			fourth.train(string4); //trains string
			System.out.println("------------------------------\nabracadabra: PST L=3 Pmin=" + fourth.getPMin() + "\n------------------------------");
			fourth.print(); //prints string
			System.out.println("\n");
			
			
			String s5 = "acadaacbda";
			String[] str5 = s5.split("", s5.length());
			ArrayList<String> string5 = new ArrayList<String>();
			for (int i = 0; i < str5.length; i++) { //makes given string into an arraylist of strings
				string5.add(str5[i]);
			}
			fifth.train(string5); //trains string
			System.out.println("------------------------------\nacadaacbda: PST L=3 Pmin=" + fourth.getPMin() + "\n------------------------------");
			fifth.print(); //prints string
			System.out.println("\n");
			
			
			String s6 = "abcccdaadcdaabcadad";
			String[] str6 = s6.split("", s6.length());
			ArrayList<String> string6 = new ArrayList<String>();
			for (int i = 0; i < str6.length; i++) { //makes given string into an arraylist of strings
				string6.add(str6[i]);
			}
			sixth.train(string6); //trains string
			System.out.println("------------------------------\nabcccdaadcdaabcadad: PST L=3 Pmin=" + fourth.getPMin() + "\n------------------------------");
			sixth.print(); //prints string
			System.out.println("\n");
		
			
			
			ArrayList<Integer> pitch2; 
			pitch2 = midiNotes.getPitchArray(); //assigns all of the note pitches from the midi file to the ArrayList pitch	
			prePitch2.train(pitch2);
			
			System.out.println("------------------------------\nMary Had a Little Lamb: PST L=3 Pmin=" + fourth.getPMin() + "\n------------------------------");
			prePitch2.print();
			
			
		}
		else if (key == '7') {
			Tree<String> first = new Tree<String>();
			String s1 = "abracadabra"; 
			String[] str1 = s1.split("", s1.length());
			ArrayList<String> string1 = new ArrayList<String>();
			for (int i = 0; i < str1.length; i++) { //makes given string into an arraylist of strings
				string1.add(str1[i]);
			}
			first.train(string1); //trains string
			System.out.println("------------------------------\nabracadabra: PST L=" + first.getL() + "\n------------------------------");
			first.print(); //prints string
			System.out.println("\n");
		}
	}
}

