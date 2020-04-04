
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyTomcat {
	public static void main(String args[]) throws Exception {
		ServerSocket server = new ServerSocket(8181);
		while(true) {
		Socket client = server.accept();
		try {
			// 获取到客户端输入流
			InputStream in = client.getInputStream();
			// 准备一个缓冲数组
			byte data[] = new byte[4096];
			//将数据读取到字节数组中,同时返回读取长度
			in.read(data);
			// 打印浏览器发来的请求头
		    // 按指定模式在字符串查找
		    String lin = String.valueOf(new String(data));
		    // 获取url的正则
		    String pattern = "[^/]+/([^\\s]*)";
		    // 创建 Pattern 对象
		    Pattern r = Pattern.compile(pattern);
		    // 现在创建 matcher 对象
		    Matcher m = r.matcher(lin);
		    String url = "";
		    if (m.find( )) {
		       // 得到url,不然还是空
		       url = m.group(1);
		    } else {
		       System.out.println("NO MATCH");
		    }
			if(url.equals(""))
				url = "index.html";
			System.out.println(new String(data));
			// 制作响应报文
			StringBuffer response = new StringBuffer();
			// 响应状态
			response.append("HTTP/1.1 200 OK\r\n");
			// 响应头
			response.append("Content-type:textml\r\n\r\n");
			// 要返回的内容
			FileInputStream input = null;
			// 获取客户端的输出流
			OutputStream out = client.getOutputStream();
			byte[] buffer = new byte[1024*1024];
			try {
				// 足够大的缓冲区可以发送图片等等
				input = new FileInputStream(url);
				input.read(buffer);
				out.write(response.toString().getBytes());
				input.close();
				out.write(buffer);
			}catch(FileNotFoundException e) {
				response.append("404 没查到你要找的内容！");
				out.write(response.toString().getBytes());
			}
			// 分开传输(内容有可能有图片或视频等等)
			// 关闭客户端和服务端的流和Socket
			out.close();
			in.close();
			}
		catch(Exception e) {
				System.out.println("ERROR...");
				e.printStackTrace();
			}
		finally {
				client.close();
			}
		}
	}
}