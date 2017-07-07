package com.spinach.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtils {

	private static Log logger = LogFactory.getLog(FileUtils.class);

	/**
	 * 从输入流将数据拷贝到输出流
	 * 
	 * @param in 输入流
	 * @param out 输出流
	 * @throws Exception
	 */
	public static void outputData(InputStream input, OutputStream output, int blockSize) throws Exception {
		int streamLen = input.available();
		int blockNums = streamLen / blockSize;
		if ((streamLen % blockSize) > 0) {
			blockNums++;
		}

		// 传输数据
		byte[] b = new byte[blockSize];
		int dataLen = 0;
		for (int i = 0; i < blockNums; i++) {
			dataLen = input.read(b, 0, blockSize);
			output.write(b, 0, dataLen);
			Thread.sleep(2);
		}
	}

	/**
	 * 默认一个字节一个字节进行传输
	 * @param in 输入流
	 * @param out 输出流
	 * @throws Exception
	 */
	public static void outputData(InputStream input, OutputStream output) throws Exception {
		int i;
		while ((i = input.read()) != -1)
			output.write(i);
	}

	/**
	 * 从parameter.ini中读取某一参数的值
	 * 
	 * @param paraName 参数
	 * @return 参数值
	 * @throws IOException
	 */
	public static String getSystemParameter(Class cls, String paraName) throws IOException {
		String value = null;
		InputStream in = null;// 配置文件输入流
		try {
			String cfgFileName = "system.ini";

			ClassLoader cl = cls.getClassLoader();
			if (cl != null) {
				in = cl.getResourceAsStream(cfgFileName);
			} else {
				in = ClassLoader.getSystemResourceAsStream(cfgFileName);
			}
			Properties properties = new Properties();
			// 加载到属性中
			properties.load(in);
			value = consume(properties, paraName);

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception ex) {
					logger.error(ex, null);
				}
			}
		}
		return value;
	}

	/**
	 * 从属性表中获取某一属性的字符串值
	 * 
	 * @param p 属性表对象
	 * @param key 属性名称
	 * @return 属性值
	 */
	public static String consume(Properties p, String key) {
		String s = null;
		if ((p != null) && (key != null))
			s = p.getProperty(key);

		return s;
	}

	/**
	 * 从属性表中获取某一属性代表的数值
	 * 
	 * @param p 属性表对象
	 * @param key 属性名称
	 * @return 属性代表的数值
	 */
	public static int consumeInt(Properties p, String key) {
		int n = -1;

		String value = consume(p, key);
		if (value != null) {
			n = Integer.parseInt(value);
		}

		return n;
	}

	/**
	 * 读取流中的内容
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static String readStream(InputStream input) throws IOException {

		StringBuilder strBuff = new StringBuilder();
		InputStreamReader insr = null;
		BufferedReader br = null;

		try {
			insr = new InputStreamReader(input);
			br = new BufferedReader(insr);
			String line = null;

			while ((line = br.readLine()) != null) {
				strBuff.append(line);
			}
		} finally {

			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				logger.error(e, null);

			}
			try {
				if (insr != null)
					insr.close();
			} catch (IOException e) {
				logger.error(e, null);
			}
			try {
				if (input != null)
					input.close();
			} catch (IOException e) {
				logger.error(e, null);
			}
		}
		return strBuff.toString();

	}

	/**
	 * 拷贝文件
	 * 
	 * @param sourcePath
	 * @param targetPath
	 * @throws Exception
	 */
	public static void copyFile(String sourcePath, String targetPath) throws Exception {
		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			fis = new FileInputStream(sourcePath);
			fos = new FileOutputStream(targetPath);
			// 替换现有文件内容
			outputData(fis, fos, 1024);
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				logger.error(e, null);
			}
			try {
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				logger.error(e, null);
			}
		}

	}

	/**
	 * 读取文件中的内容。
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static List<String> readFile(String filePath) throws Exception {

		List<String> list = new ArrayList<String>();

		InputStream inputs = null;

		BufferedReader br = null;
		InputStreamReader read = null;
		String data = "";
		if (!new File(filePath).exists())
			return list;

		try {
			inputs = new FileInputStream(filePath);
			read = new InputStreamReader(inputs);
			br = new BufferedReader(read);
			while ((data = br.readLine()) != null) {
				list.add(data);
			}
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {

			}
			try {
				if (read != null)
					read.close();
			} catch (IOException e) {

			}
			try {
				if (inputs != null)
					inputs.close();
			} catch (IOException e) {

			}

		}

		return list;
	}
	
	public static void main(String[] args) {
		FileUtils.writeAppendFile("E:\\123\\11\\fdfdfdf\\123","443.log" ,"fdafffffffffffffffdsafdsa fdsa fdsa 魂牵梦萦地魂牵梦萦  城");
	}
	public static void writeAppendFile(String filePath,String fileName, String strContent) {
		File _filePath = new File(filePath);
		File _fileName = new File(filePath+File.separator+fileName);
		FileWriter writer = null;
		try {
			if(!_filePath.exists()){
				_filePath.mkdirs();
			}
			if (!_fileName.exists()) {
				_fileName.createNewFile();
			}
			writer = new FileWriter(_fileName, true);
			writer.write(strContent + "\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				logger.error(e, null);
			}
		}
	}

	/**
	 * 写文本文件
	 * 
	 * @param filePath
	 * @param strContent
	 * @param charset
	 * @throws Exception
	 */
	public static void writeFile(String filePath, String strContent, String charset) throws Exception {

		FileOutputStream fileOutputStream = null;
		OutputStreamWriter out = null;
		BufferedWriter bw = null;
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdir();
		}

		try {
			fileOutputStream = new FileOutputStream(filePath);
			if (charset != null && !"".equals(charset)) {
				out = new OutputStreamWriter(fileOutputStream, charset);
			} else {
				out = new OutputStreamWriter(fileOutputStream);
			}
			bw = new BufferedWriter(out);
			bw.write(strContent);
			bw.flush();
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				logger.error(e, null);
			}
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				logger.error(e, null);
			}
			try {
				if (fileOutputStream != null)
					fileOutputStream.close();
			} catch (IOException e) {
				logger.error(e, null);
			}
		}

	}

	/**
	 * 写文本文件
	 * 
	 * @param filePath
	 * @param strContent
	 * @throws Exception
	 */
	public static void writeFile(String filePath, String strContent) throws Exception {
		writeFile(filePath, strContent, "");
	}

	/**
	 * 按指定编码写文本文件
	 * 
	 * @param filePath
	 * @param list
	 * @param charset
	 * @throws Exception
	 */
	public static void writeFile(String filePath, List<String> list, String charset) throws Exception {
		StringBuilder sb = new StringBuilder();

		if (!new File(filePath).exists())
			return;

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i));
				sb.append("\r\n");
			}
			writeFile(filePath, sb.toString(), charset);
		} else {
			writeFile(filePath, "", charset);
		}
	}

	/**
	 * 写文本文件
	 * 
	 * @param filePath
	 * @param list
	 * @throws Exception
	 */
	public static void writeFile(String filePath, List<String> list) throws Exception {
		writeFile(filePath, list, "");
	}

	/**
	 * <p>
	 * 对InputStream/OutputStream/Reader/Writer文件流 和 HttpURLConnection进行逐个关闭
	 * </p>
	 * 
	 * @param fileStreams
	 */
	public static void closeStream(Object... fileStreams) {
		if (fileStreams == null)
			return;
		for (Object fileStream : fileStreams) {
			if (fileStream == null)
				continue;
			InputStream is = null;
			OutputStream out = null;
			Reader reader = null;
			Writer writer = null;
			HttpURLConnection urlConnection = null;
			if (fileStream instanceof InputStream) {
				is = (InputStream) fileStream;
				if (!CommonTool.isNull(is)) {
					try {
						is.close();
					} catch (IOException e) {
						logger.error("文件流关闭出错", e);
					}
					is = null;
				}
			} else if (fileStream instanceof OutputStream) {
				out = (OutputStream) fileStream;
				if (!CommonTool.isNull(out)) {
					try {
						out.flush();
						out.close();
					} catch (IOException e) {
						logger.error("文件流关闭出错", e);
					}
					out = null;
				}
			} else if (fileStream instanceof Reader) {
				reader = (Reader) fileStream;
				if (!CommonTool.isNull(reader)) {
					try {
						reader.close();
					} catch (IOException e) {
						logger.error("文件流关闭出错", e);
					}
					reader = null;
				}
			} else if (fileStream instanceof Writer) {
				writer = (Writer) fileStream;
				if (!CommonTool.isNull(writer)) {
					try {
						writer.flush();
						writer.close();
					} catch (IOException e) {
						logger.error("文件流关闭出错", e);
					}
				}
			} else if (fileStream instanceof HttpURLConnection) {
				urlConnection = (HttpURLConnection) fileStream;
				if (!CommonTool.isNull(urlConnection)) {
					try {
						urlConnection.disconnect();
					} catch (Exception e) {
						logger.error("关闭URL链接出错：", e);
					}
				}
			}
		}
	}

}
