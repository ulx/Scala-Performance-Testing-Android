package com.rusin.scalapefomance

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.{Toast, Button, EditText, TextView}
import android.os.Debug

import scala.util.Random

object MainActivity{
	 var PATH: String = ""
}

class MainActivity extends Activity{

	val icount = new Debug.InstructionCount
	var output = ""
  val COUNT_POPULATE = 1000

	override def onCreate(savedInstanceState:Bundle) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main)
		startProgram
		var list = populateList
		MainActivity.PATH = getApplicationContext.getFilesDir.getAbsolutePath

		findViewById(R.id.Button02).asInstanceOf[android.widget.Button].setOnClickListener(new OnClickListener(){
			override def onClick(view: View){
				val start = timer
				list = StoreString quicksort list
				val end = timer
				writeOutput(start,end,"Sorting = ")
				FileHandling writeList list
				findViewById(R.id.TextView01).asInstanceOf[android.widget.TextView] setText "Status: Sorted" + " Sorting time = " + (end - start)
				Toast.makeText(getApplicationContext, "Searching time = " + (end - start), Toast.LENGTH_SHORT).show
			}
		})

		findViewById(R.id.Button03).asInstanceOf[android.widget.Button].setOnClickListener(new OnClickListener(){
			var str = ""
			override def onClick(view: View){
				val start = timer
				val i =  StoreString binarySearch(findViewById(R.id.EditText01).asInstanceOf[android.widget.EditText].getText().toString(), list, 0, (list.length-1))
				val end = timer
				writeOutput(start, end, "Searching time = ")
				Toast.makeText(getApplicationContext, "Searching time = " + (end - start), Toast.LENGTH_SHORT).show
				if (i > -1)
					str = "Element found in position " + i + " in the Array List"
					else if ( i == -1)
						str = "Search unsuccessfull!!" + " Searching time = " + (end - start);
						findViewById(R.id.TextView01).asInstanceOf[android.widget.TextView].setText(str)
						collectResults
			}
		})

		findViewById(R.id.Button04).asInstanceOf[android.widget.Button].setOnClickListener(new OnClickListener(){
			override def onClick(view: View){
				System exit 1
			}
		})   
	}

	def startProgram() {
	  icount.resetAndStart
	  Debug startMethodTracing("st")
     }

	def populateList() = {
		val random = new Random
		var list = List[RandomString](new RandomString(random.nextInt(10000),random.nextInt(100)))
		for (i <- 0 until COUNT_POPULATE){
			val rs = new RandomString(random.nextInt(10000),random.nextInt(100))
			list = rs :: list
		}
		list
	}

	def collectResults(){
		if (icount collect ) { 
			val totalInstructions = "Total instructions executed: " + icount.globalTotal + "\n" 
			output = output + "\n" + totalInstructions
			val methodInvocations = "Method invocations: " + icount.globalMethodInvocations + "\n" 
			output = output + "\n" + methodInvocations }
		Debug.stopMethodTracing
		FileHandling writeLog(output + "")
	}

	def timer() = System.currentTimeMillis

	def writeOutput(start: Long, end: Long, s: String){
		output = output + s + (end-start)+ "\n"
	}
}