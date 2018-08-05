package com.example.titas.realtimechatapp.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.titas.realtimechatapp.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private var mListener: OnLoginFragmentInteractionListener? = null
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mView =  inflater!!.inflate(R.layout.fragment_login, container, false)
        return mView
    }

    override fun onResume() {
        super.onResume()
        btn_sign_in.setOnClickListener {
            if(mListener != null && username.text.isNotEmpty()){
                mListener?.onSignInButtonClicked(username.text.toString())
            } else {
                Snackbar.make(mView, "Please enter a valid username", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnLoginFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


    interface OnLoginFragmentInteractionListener {
        fun onSignInButtonClicked(userName: String)
    }

    companion object {
        fun newInstance(): LoginFragment {
            val fragment = LoginFragment()
            return fragment
        }
    }
}
