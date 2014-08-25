package tfidf

import java.io.File

object Util {

  /**
   * Get all full file names under a directory recursively
   */
  def textFile(path: File): Vector[File] = {
    val these = path.listFiles
    ( these ++ these.filter(_.isDirectory).flatMap(d => textFile(d)) ).filter(!_.isDirectory).toVector
  }

  def textFile(path: String): Vector[File] = this.textFile(new File(path))
  
  /**
   * 
   */
  def deleteDir(dir: String): Unit = deleteDir(new File(dir)) 
  
  /**
   * Delete a directory
   */
  def deleteDir(dir: File): Unit = {
    if(dir.isDirectory){
      // Directory is empty, then delete it
      if(dir.list().length == 0){
        dir.delete
      } else {
        val files = dir.list
        files.foreach(f => deleteDir(new File(dir, f)))
        
        if(dir.list.length==0){
          dir.delete
        }
        
      }
    
    } else {
      dir.delete
    }
  }
}