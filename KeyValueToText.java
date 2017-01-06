import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;



public class KeyValueToText {
  public static class KeyValueMapper extends Mapper<LongWritable,Text,LongWritable,Text>{
	  public void map(LongWritable key, Text value, Context context)throws IOException, InterruptedException {
		  context.write(key, value);
	  }
  }
	
public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		conf.set("mapreduce.input.keyvaluelinerecorder.key.value.seprator","#");
		conf.set("mapreduce.output.textoutputformat.seprator",",");
		Job job = new Job(conf,"Converting Sequence to Text");
	    job.setJarByClass(KeyValueToText.class);
	    job.setMapperClass( KeyValueMapper.class);
	    job.setNumReduceTasks(0);
	    job.setOutputKeyClass(LongWritable.class);
	    job.setOutputValueClass(Text.class);
	    job.setOutputFormatClass(SequenceFileOutputFormat.class);
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	  
}
}