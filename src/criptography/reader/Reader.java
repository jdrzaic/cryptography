package criptography.reader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Reader {

	private List<String> contents;
	
	private String path;
	
	public Reader(String documentsPath) {
		this.path = documentsPath;
		this.contents = new ArrayList<String>();
	}
	
	public void loadContents() {
		try{
			Files.walkFileTree(Paths.get(path), new ReadFilesVisitor());
			if(contents.isEmpty()) {
				throw new InvalidParameterException("Path not valid or target folder empty, please try again!");
			}
		}catch(IOException e) {
		}
	}
	
	private class ReadFilesVisitor implements FileVisitor<Path> {

		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attrs) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(
					 new InputStreamReader(
					 new BufferedInputStream(
					 new FileInputStream(file.toFile())),"UTF-8"));
			String line;
			while((line = br.readLine()) != null) {
				line = line.replaceAll("\\P{L}+", "");
				line = removeUnicodes(line);
				sb.append(line);
			}
			br.close();
			contents.add(sb.toString());
			return FileVisitResult.CONTINUE;
		}

		private String removeUnicodes(String line) {
			int len = line.length();
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < len; ++i) {
				sb.append(removeUnicode(line.charAt(i)));
			}
			return sb.toString();
		}

		private String removeUnicode(char c) {
			boolean upper = false;
			String changed;
			if(Character.compare(c, Character.toLowerCase(c)) != 0) {
				upper = true;
			}
			switch (Character.toLowerCase(c)) {
			case 'č':
				changed = "c";
				break;
			case 'ć':
				changed = "c";
				break;
			case 'ž':
				changed = "z";
				break;
			case 'đ':
				changed = "dj";
				break;
			case 'š':
				changed = "s";
				break;
			default:
				changed = String.valueOf(c);
			}
			return changed.toUpperCase();
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc)
				throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc)
				throws IOException {
			return FileVisitResult.CONTINUE;
		}
	}
	
	public List<String> getContents() {
		return contents;
	}
	
}
