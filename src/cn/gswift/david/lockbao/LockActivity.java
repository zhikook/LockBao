package cn.gswift.david.lockbao;

import android.app.Activity; 
import android.app.admin.DevicePolicyManager; 
import android.content.ComponentName; 
import android.content.Context; 
import android.content.Intent; 
import android.os.Bundle; 
   
public class LockActivity extends Activity { 
   
    private DevicePolicyManager policyManager; 
   
    private ComponentName componentName; 
   
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
   
        super.onCreate(savedInstanceState); 
   
        setContentView(R.layout.activity_main); 
        policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE); 
        componentName = new ComponentName(this, LockReceiver.class); 
        if (policyManager.isAdminActive(componentName)) {//�ж��Ƿ���Ȩ��(�������豸������) 
            policyManager.lockNow();// ֱ������ 
            android.os.Process.killProcess(android.os.Process.myPid()); 
        }else{ 
            activeManager();//�����豸��������ȡȨ�� 
        } 
   
    } 
   
   
    @Override 
    protected void onResume() {//��д�˷��������ڵ�һ�μ����豸������֮��������Ļ 
        if (policyManager.isAdminActive(componentName)) { 
            policyManager.lockNow(); 
            android.os.Process.killProcess(android.os.Process.myPid()); 
        } 
        super.onResume(); 
    } 
   
    private void activeManager() { 
        //ʹ����ʽ��ͼ����ϵͳ����������ָ�����豸������ 
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN); 
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName); 
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "һ������"); 
        startActivity(intent); 
    } 
   
} 