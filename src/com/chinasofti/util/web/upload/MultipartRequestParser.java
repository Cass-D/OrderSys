/**
 *  Copyright 2014 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.web.upload;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.chinasofti.util.bean.BeanUtil;
import com.chinasofti.util.bean.convertor.TypeConvertor;

/**
 * <p>
 * Title: MultipartRequestParser
 * </p>
 * <p>
 * Description: �����ϴ��ļ����ݵ�Http�����������
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015
 * </p>
 * <p>
 * Company: ChinaSoft International Ltd.
 * </p>
 * 
 * @author etc
 * @version 1.0
 */
public class MultipartRequestParser {

	/**
	 * ����Bean����ֵ�ù��ߣ��ڱ����е���Ҫ�����ǻ�ȡ�����������͵�ת����
	 * */
	private BeanUtil beanUtil = new BeanUtil();
	/**
	 * ���ڴ��HTTP�����е��ı����Ͳ�����ɢ�б�
	 * */
	private Hashtable<String, String[]> txtParameters = new Hashtable<String, String[]>();
	/**
	 * ���ڴ��HTTP�������ļ����Ͳ�����ɢ�б�
	 * */
	private Hashtable<String, FormFile[]> fileParameters = new Hashtable<String, FormFile[]>();

	/**
	 * �����������
	 * 
	 * @param request
	 *            http�������
	 * @param bean
	 *            ��Ҫ����http�����еĲ����Զ�����javabean,Ҫ��bean�е�����ӵ�ж�Ӧ��setter������
	 *            �������һ����������Ӧ�������ֵ�������bean��Ӧ�ö�������
	 * @return ���������ϵ�javaBean����
	 * */
	public Object parse(HttpServletRequest request, Object bean) {

		try {
			publishProgress(request, 0);
			// ��ȡsession�ػ�����
			HttpSession session = request.getSession(true);
			// ��ȡ����ͷ���������д��ڵ�"������=����ֵ"��ʽ���ڵĲ������Ƽ���
			Enumeration<String> parameterNames = request.getParameterNames();
			// ��������������
			while (parameterNames.hasMoreElements()) {
				// ��ȡ������һ��������
				String parameterName = parameterNames.nextElement();
				// ��ȡ�ò�������Ӧ�Ĳ���ֵ����
				String[] values = request.getParameterValues(parameterName);
				// ���������Ͷ�Ӧ��ֵ���ϴ���ɢ�б���
				txtParameters.put(parameterName, values);
			}
			// ��ȡhttp�����mime����
			String contentType = request.getContentType();
			// ����http����mime�����ж��Ƿ��ϴ����ļ�
			if ("post".equalsIgnoreCase(request.getMethod())
					&& contentType.toLowerCase().startsWith(
							"multipart/form-data;")) {

				// ��ȡ����ָ���������
				String boundary = contentType.substring(contentType
						.indexOf("boundary=") + 9);
				// ��ȡ�����Ӧ��������
				ServletInputStream sis = request.getInputStream();
				// ��ȡ����ĳ��ȣ������б�������+������Ϣ�ĺϼƴ�С
				int length = request.getContentLength();
				// �ڻػ��д����ʾ��ʼ�ϴ���״̬��Ϣ
				session.setAttribute(IUploadInfo.UPLOAD_STATE,
						IUploadInfo.UPLOAD_STATE_UPLOADING);
				// ������ȡ���ݵĻ�����
				byte[] buf = new byte[4096];
				// ��ȡ�ֽڼ�����
				int c = 0;
				// �ܹ���ȡ�ֽ����ļ�����
				int readCounter = 0;
				// ѭ����ȡ
				while ((c = sis.readLine(buf, 0, buf.length)) != -1) {
					// �ۼӶ�ȡ�ֽ���
					readCounter += c;
					// ����ȡ������ת��Ϊ�ַ���
					String msg = new String(buf, 0, c, "utf-8");
					// �����ȡ���������а���"filename="""�ַ���ʱ˵����ȡ���˱���������Ϣ
					if (msg.indexOf("filename=\"") != -1) {
						// ����һ���ϴ��ļ�����
						FormFile fileParameter = new FormFile();
						// �����ȡ�ļ����ı���
						String fileName = "";
						// ��ȡ�ϴ����ļ���
						fileName = msg
								.substring(msg.indexOf("filename=\"") + 10);
						fileName = fileName
								.substring(0, fileName.indexOf("\""));
						fileName = fileName.substring(fileName
								.lastIndexOf("\\") + 1);

						// �����ϴ��ļ�������ļ�����Ϣ
						fileParameter.setFileName(fileName);
						// ��ȡ���������
						String name = msg.substring(msg.indexOf("name=\"") + 6);
						name = name.substring(0, name.indexOf("\""));
						// ��ȡ�ļ����MIME��Ϣ��
						c = sis.readLine(buf, 0, buf.length);
						// �ۼӶ�ȡ������
						readCounter += c;
						// ��MIME������ת��Ϊ�ַ���
						String s = new String(buf, 0, c);
						// ��ȡ�ļ���MIME����
						String contextType = s.substring(s.indexOf(":") + 1)
								.trim();
						// �����ļ��ϴ������mime������Ϣ
						fileParameter.setContextType(contextType);
						// ��ȡMIME��Ϣ���һ������
						c = sis.readLine(buf, 0, buf.length);
						// �ۼӼ�����
						readCounter += c;
						// ��ȡ��ǰWEB��Ŀ�е�temp�ļ������ڱ����ϴ�����ʱ�ļ�
						String path = request.getSession().getServletContext()
								.getRealPath("/WEB-INF/temp");
						
						
						//�����ʱ�ļ��в������򴴽�һ���µ���ʱ�ļ���
						File testPath=new File(path);
						if(!testPath.exists()){
							testPath.mkdir();
						}
						
						
						
						// ������ʱ�ļ�����
						File tempFile = File.createTempFile("httpupload",
								".uploadtemp", new File(path));
						// ���������ʱ�ļ��������
						FileOutputStream fos = new FileOutputStream(tempFile);
						// ѭ����ȡ�ļ�����е�����
						while ((c = sis.readLine(buf, 0, buf.length)) != -1) {
							// �ۼӼ�����
							readCounter += c;
							// �����ϴ�������Ϣ
							publishProgress(request, length, readCounter);
							// ����ȡ��������ת��Ϊ�ַ���
							msg = new String(buf, 0, c);
							// �ж��Ƿ�Ϊ��������������������Ľ���������ǣ�������ѭ��ִ��
							if (msg.trim().equals("--" + boundary)
									|| msg.trim()
											.equals("--" + boundary + "--")) {
								break;
							}
							// ������Ǳ������������Ľ���������ȡ�������ݿ�������ʱ�ļ���
							fos.write(buf, 0, c);
						}
						// ˢ�������
						fos.flush();
						// �ر��ļ������
						fos.close();
						// �����ϴ��ļ�����ʱ�ļ���Ϣ
						fileParameter.setTempFile(tempFile);
						// ���ϴ����ļ����뵽�ļ������б���
						addFileParameter(name, fileParameter);
						// ���û��filename��ֻ��name˵����ȡ����ͨ���ı�������
					} else if (msg.indexOf("name=\"") != -1) {
						// ��ȡ���������
						String name = msg.substring(msg.indexOf("name=\"") + 6);
						name = name.substring(0, name.indexOf("\""));
						// ��ȡ����˵�����һ������
						c = sis.readLine(buf, 0, buf.length);
						// �ۼӼ�����
						readCounter += c;
						// �������������������
						String value = "";
						// ѭ����ȡ���������
						while ((c = sis.readLine(buf, 0, buf.length)) != -1) {
							// �ۼӶ�ȡ������
							readCounter += c;
							// ����ȡ��������ת��Ϊ�ַ���
							String s = new String(buf, 0, c, "utf-8");
							// �ж��Ƿ�Ϊ��������������ĩβ�������������ѭ��ִ��
							if (s.trim().equals("--" + boundary)
									|| s.trim().equals("--" + boundary + "--")) {
								break;
							}
							// �����ζ�ȡ�������ݴ��������ݱ�����
							value += s;
						}
						// ��ȡ����ַ�����byte������ʽ
						byte[] valueByte = value.getBytes();
						// �����ַ���ĩβ��2���ֽڵ�http���������ַ�
						addTxtParameter(name, new String(valueByte, 0,
								valueByte.length - 2));
						// ����������Ϣ
						publishProgress(request, length, readCounter);
						// ������������β��������ѭ��ִ��
					} else if (msg.trim().equals("--" + boundary + "--")) {
						break;
					}
				}
				// ��ȡ�����󽫽�����Ϣ����Ϊ100%
				publishProgress(request, 100);
				
			}
			// ���ö�ȡ�����������javaBean
			fillBean(bean);
		} catch (Exception e) {
			// ��������쳣������쳣��Ϣ
			e.printStackTrace();
		}
		// �������javaBean���󷵻�
		return bean;
	}

	/**
	 * ���JavaBean�ķ���
	 * 
	 * @param bean
	 *            ��Ҫ����JavaBean����
	 * */
	private void fillBean(Object bean) {

		try {
			// ������ʡ��ȡBean��������Ϣ
			BeanInfo info = Introspector.getBeanInfo(bean.getClass());
			// ��ȡBean���������Ե�����
			PropertyDescriptor[] pds = info.getPropertyDescriptors();
			// ��ȡ����ı���������ɢ�б�����м�
			Enumeration<String> txtKeys = txtParameters.keys();
			// �������е��ı�����
			while (txtKeys.hasMoreElements()) {
				// ��ȡһ���ض��ı�����
				String key = txtKeys.nextElement();
				// ��ȡ��������Ӧ��ֵ����
				String[] values = txtParameters.get(key);
				// ����JavaBean��������������
				for (PropertyDescriptor pd : pds) {
					// �����Bean���ҵ��ͱ�����ͬ�������ԣ���ִ��������
					if (pd.getName().equals(key)) {
						// ��ȡ�����Ե�setter����
						Method setMethod = pd.getWriteMethod();
						// ��ȡsetter�����ĵ�һ���������ͣ�setter����ֻ��һ��������
						Class argType = setMethod.getParameterTypes()[0];
						// ���Bean�ж�������Բ�Ϊ����
						if (!argType.isArray()) {
							// ȡ������ĵ�һ��ֵ
							String value = values[0];
							// ����ִ�з����Ĳ�������
							Object objValue = value;
							// ��ȡ����ת����
							TypeConvertor convertor = beanUtil.getConvertors()
									.get(argType.getCanonicalName());
							// ���ת��������գ����ö�Ӧ�������͵�ת�������ַ���ת��ΪĿ������
							if (convertor != null) {
								objValue = convertor.convertToObject(value);
							}
							// ���÷������setter����
							setMethod.invoke(bean, new Object[] { objValue });
						} else {
							// ��������飬�򴴽���ԭʼ����Ԫ�ظ�����ͬ�Ĳ�������
							Object[] objValues = new Object[values.length];
							// ��ȡ������ÿ��Ԫ�ص�����
							Class elementType = argType.getComponentType();
							// ��ȡ����Ԫ�ص�����ת����
							TypeConvertor convertor = beanUtil.getConvertors()
									.get(elementType.getCanonicalName());
							// ѭ��������������
							for (int i = 0; i < objValues.length; i++) {
								// ���ת������Ϊ�գ���ÿһ������ת��ΪĿ������
								if (convertor != null) {
									objValues[i] = convertor
											.convertToObject(values[i]);
									// ���ת����Ϊ�գ���ִ��ת������
								} else {
									objValues[i] = values[i];
								}
							}
							// ִ��setter����
							setMethod.invoke(bean, new Object[] { objValues });
						}
					}
				}
			}
			// ��ȡ�����ļ���������
			Enumeration<String> fileKeys = fileParameters.keys();
			// �����ļ���������
			while (fileKeys.hasMoreElements()) {
				// ��ȡ�����ļ���������
				String key = fileKeys.nextElement();
				// ��ȡ�ļ�������ƶ�Ӧ������
				FormFile[] files = fileParameters.get(key);
				// ѭ������bean����������
				for (PropertyDescriptor pd : pds) {
					// ����ҵ����ļ��������ͬ����������ִ����Ӧ��setter����
					if (pd.getName().equals(key)) {
						// ��ȡsetter����
						Method setMethod = pd.getWriteMethod();
						// ��ȡsertter�����Ĳ�������
						Class argType = setMethod.getParameterTypes()[0];
						// ���setter�����Ĳ��������飬��ֱ������ԭʼ����������Ϊ��������setter����
						if (argType.isArray()) {
							setMethod.invoke(bean, new Object[] { files });
							// ���setter�����Ĳ����������飬������ԭʼ��������ĵ�һ��Ԫ����Ϊ��������setter����
						} else {
							setMethod.invoke(bean, files[0]);
						}
					}
				}
			}

		} catch (Exception ex) {
			// ��������쳣������쳣��Ϣ
			ex.printStackTrace();
		}

	}

	/**
	 * ���һ���ض����ֵ��ı�����
	 * 
	 * @param pname
	 *            ��������
	 * @param pvalue
	 *            ����ֵ
	 * */
	private void addTxtParameter(String pname, String pvalue) {

		// �ж��Ƿ����ͬ���Ĳ���
		if (txtParameters.containsKey(pname)) {
			// ��ȡ������Ӧ��ԭʼ��������
			String[] values = txtParameters.get(pname);
			// ��չ�������飬��Ԫ�ظ�������1
			String[] newValues = Arrays.copyOf(values, values.length + 1);
			// �������ݴ�������������һ��Ԫ��
			newValues[values.length] = pvalue;
			// ����������Ϊ������Ӧ�����ݴ���ɢ�б�
			txtParameters.put(pname, newValues);
		} else {
			// ���ԭ��������ͬ���Ĳ�������ɢ�б��д����µ�����
			txtParameters.put(pname, new String[] { pvalue });
		}

	}

	/**
	 * ���һ���ض����ֵ��ļ�����
	 * 
	 * @param pname
	 *            ��������
	 * @param flie
	 *            ����ֵ
	 * */
	private void addFileParameter(String pname, FormFile file) {
		// �ж��Ƿ����ͬ���Ĳ���
		if (fileParameters.containsKey(pname)) {
			// ��ȡ������Ӧ��ԭʼ��������
			FormFile[] values = fileParameters.get(pname);
			// ��չ�������飬��Ԫ�ظ�������1
			FormFile[] newValues = Arrays.copyOf(values, values.length + 1);
			// �������ݴ�������������һ��Ԫ��
			newValues[values.length] = file;
			// ����������Ϊ������Ӧ�����ݴ���ɢ�б�
			fileParameters.put(pname, newValues);
		} else {
			// ���ԭ��������ͬ���Ĳ�������ɢ�б��д����µ�����
			fileParameters.put(pname, new FormFile[] { file });
		}
	}

	/**
	 * ���㵱ǰ�ϴ�������Ϣ�ķ���
	 * 
	 * @param contentLength
	 *            ������ĳ���
	 * @param readCounter
	 *            ��ǰ��ȡ���ֽ���
	 * @return ���ؼ���õ��Ľ���ֵ
	 * */
	private Integer getProgress(int contentLength, int readCounter) {
		// ���ؽ���ֵ��������Ϊ0-100����������Ҫע�⣺1���������������������Ӧ�����ȳ���100�ٳ����ܳ��ȣ�2������100�Ժ���ܻᳬ��intֵ��ʾ��Χ����˼���ʱ�Ƚ�������ת��Ϊlong������
		return new Integer((int) (((long) readCounter) * 100 / contentLength));
	}

	/**
	 * ��ػ��з���������Ϣ�ķ���
	 * 
	 * @param request
	 *            http������Ϣ
	 * @param contentLength
	 *            ������ĳ���
	 * @param readCounter
	 *            ��ǰ��ȡ���ֽ���
	 * */
	private void publishProgress(HttpServletRequest request, int contentLength,
			int readCounter) {
		// �����ṩ��http�����ܳ��Ⱥ͵�ǰ��ȡ���ֽ���������Ⱥ����Ự������
		request.getSession(true).setAttribute(IUploadInfo.UPLOAD_PROGRESS,
				getProgress(contentLength, readCounter));
	}

	/**
	 * ��ػ��з���������Ϣ�ķ���
	 * 
	 * @param request
	 *            http������Ϣ
	 * @param progress
	 *            ��Ҫ�����Ľ���ֵ��ֵΪ0-100
	 * */
	private void publishProgress(HttpServletRequest request, int progress) {
		// �ڻỰ�����б������ֵ
		request.getSession(true).setAttribute(IUploadInfo.UPLOAD_PROGRESS,
				new Integer(progress));
	}

	/**
	 * �����������
	 * 
	 * @param request
	 *            http�������
	 * @param beanClass
	 *            ��Ҫ����http�����еĲ����Զ�����javabean��Ӧ��Class����,Ҫ��bean�е�����ӵ�ж�Ӧ��setter������
	 *            �������һ����������Ӧ�������ֵ�������bean��Ӧ�ö�������
	 * @return ���������ϵ�javaBean����
	 * @see #parse(HttpServletRequest, Object)
	 * */
	public Object parse(HttpServletRequest request, Class beanClass) {
		try {
			// ����Bean����
			Object bean = beanClass.newInstance();
			// ���ñ����������������
			return parse(request, bean);
		} catch (Exception e) {
			// ��������쳣������쳣��Ϣ
			e.printStackTrace();
			// �����쳣�������ֱ�ӷ���null
			return null;
		}

	}

	/**
	 * �����������
	 * 
	 * @param request
	 *            http�������
	 * @param beanClassName
	 *            ��Ҫ����http�����еĲ����Զ�����javabean��Ӧ��ȫ�޶�����,Ҫ��bean�е�����ӵ�ж�Ӧ��setter������
	 *            �������һ����������Ӧ�������ֵ�������bean��Ӧ�ö�������
	 * @return ���������ϵ�javaBean����
	 * @see #parse(HttpServletRequest, Class)
	 * */
	public Object parse(HttpServletRequest request, String beanClassName) {
		try {
			// ���ظ�����java��
			Class cls = Class.forName(beanClassName);
			// ���ñ����������������
			return parse(request, cls);
		} catch (Exception e) {
			// ��������쳣������쳣��Ϣ
			e.printStackTrace();
			// �����쳣�������ֱ�ӷ���null
			return null;
			// TODO: handle exception
		}

	}

	/**
	 * ��ȡ������Ϣ�ķ���
	 * 
	 * @param request
	 *            http�������
	 * @return ���ػ�ȡ���Ľ���ֵ������ֵΪ0-100
	 * */
	public static int getUploadProgress(HttpServletRequest request) {
		try {
			// ����ػ������д��ڽ�����Ϣ������ת��Ϊintֵ�󷵻�
			return ((Integer) request.getSession(true).getAttribute(
					IUploadInfo.UPLOAD_PROGRESS)).intValue();
		} catch (Exception e) {
			// ����ػ������в����ڽ�����Ϣ���򷵻�0
			return 0;
		}
	}

}
