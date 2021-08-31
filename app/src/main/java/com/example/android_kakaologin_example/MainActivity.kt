package com.example.android_kakaologin_example

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class MainActivity : AppCompatActivity() {

    companion object {
        const val KAKAO_NATIVE_APP_KEY = ""
    }

    private lateinit var activityMainButtonGetKeyhash: Button
    private lateinit var activityMainConstraintlayoutKakao: ConstraintLayout
    private lateinit var activityMainButtonGetUserInfo: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityMainButtonGetKeyhash = findViewById(R.id.activity_main_button_get_keyhash)
        activityMainConstraintlayoutKakao = findViewById(R.id.activity_main_constraintlayout_kakao)
        activityMainButtonGetUserInfo = findViewById(R.id.activity_main_button_get_user_info)

        /*
        카카오 로그인 방법
        1. 카카오 개발센터(https://developers.kakao.com/console/app)에서 애플리케이션 추가
        2. (내 애플리케이션 -> 앱 설정 -> 플랫폼)에서 Android 플랫폼 등록 (키 해시 가져오는 방법: Utility.getKeyHash(this))
        3. (내 애플리케이션 -> 앱 설정 -> 요약 정보)에서 네이티브 앱 키 가져와 KAKAO_NATIVE_APP_KEY 에 주기
        4. (내 애플리케이션 -> 제품 설정 -> 카카오 로그인)에서 카카오 로그인 활성화
         */

        KakaoSdk.init(this, KAKAO_NATIVE_APP_KEY) //Kakao SDK 초기화. Application.onCreate 내에서 호출 권장.

        activityMainButtonGetKeyhash.setOnClickListener {
            Log.d("myLog", Utility.getKeyHash(this))
        }

        activityMainConstraintlayoutKakao.setOnClickListener {
            UserApiClient.instance.loginWithKakaoTalk(this) { oAuthToken, error ->
                if (error != null) {
                    Log.d("myLog", "로그인 실패", error)
                } else if (oAuthToken != null) {
                    Log.d("myLog", "로그인 성공")
                }
            }
        }

        activityMainButtonGetUserInfo.setOnClickListener {
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    Log.d("myLog", "사용자 정보 요청 실패", error)
                } else if (user != null) {
                    Log.d("myLog", "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                }
            }
        }
    }

}