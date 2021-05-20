import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

class parsertest {

	@Test
	public void Validurl() {
		jsonparser p = new jsonparser();
		String url="https://en.wikipedia.org/w/api.php?format=json&action=query&titles=SMALL&prop=revisions&rvprop=content";
		assertEquals("parse success",p.getData(url));
	}


	@Test
	public void MalformedURLException() {
		jsonparser p = new jsonparser();
		String url="https://en.wikipedaction=query&titles=SMALL&prop=revisions&rvprop=content";
		assertEquals("error with url data", p.getData(url));
	}
	@Test
	public void FileCreated() {
		jsonparser p = new jsonparser();
		String filename = "file.txt";
		File myObj = new File(filename);
		assertEquals("File Created Successfully", p.FileMaker(myObj,filename));
	}

}
