
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
			// ��ȡ���ͻ���������
			InputStream in = client.getInputStream();
			// ׼��һ����������
			byte data[] = new byte[4096];
			//�����ݶ�ȡ���ֽ�������,ͬʱ���ض�ȡ����
			in.read(data);
			// ��ӡ���������������ͷ
		    // ��ָ��ģʽ���ַ�������
		    String lin = String.valueOf(new String(data));
		    // ��ȡurl������
		    String pattern = "[^/]+/([^\\s]*)";
		    // ���� Pattern ����
		    Pattern r = Pattern.compile(pattern);
		    // ���ڴ��� matcher ����
		    Matcher m = r.matcher(lin);
		    String url = "";
		    if (m.find( )) {
		       // �õ�url,��Ȼ���ǿ�
		       url = m.group(1);
		    } else {
		       System.out.println("NO MATCH");
		    }
			if(url.equals(""))
				url = "index.html";
			System.out.println(new String(data));
			// ������Ӧ����
			StringBuffer response = new StringBuffer();
			// ��Ӧ״̬
			response.append("HTTP/1.1 200 OK\r\n");
			// ��Ӧͷ
			response.append("Content-type:textml\r\n\r\n");
			// Ҫ���ص�����
			FileInputStream input = null;
			// ��ȡ�ͻ��˵������
			OutputStream out = client.getOutputStream();
			byte[] buffer = new byte[1024*1024];
			try {
				// �㹻��Ļ��������Է���ͼƬ�ȵ�
				input = new FileInputStream(url);
				input.read(buffer);
				out.write(response.toString().getBytes());
				input.close();
				out.write(buffer);
			}catch(FileNotFoundException e) {
				response.append("404 û�鵽��Ҫ�ҵ����ݣ�");
				out.write(response.toString().getBytes());
			}
			// �ֿ�����(�����п�����ͼƬ����Ƶ�ȵ�)
			// �رտͻ��˺ͷ���˵�����Socket
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