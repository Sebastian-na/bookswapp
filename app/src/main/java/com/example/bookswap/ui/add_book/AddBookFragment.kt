package com.example.bookswap.ui.add_book

import android.content.ActivityNotFoundException
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bookswap.BuildConfig
import com.example.bookswap.R
import com.example.bookswap.databinding.FragmentAddBookBinding
import com.example.bookswap.databinding.FragmentProfileBinding
import com.example.bookswap.ui.profile.ProfileViewModel
import com.example.bookswap.utils.RealPathUtil
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import java.io.*
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class AddBookFragment : Fragment() {

    private lateinit var _binding: FragmentAddBookBinding

    private val binding get() = _binding

    private val viewModel: AddBookViewModel by viewModels()

    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            latestTmpUri?.let { uri ->
                previewImage?.setImageURI(uri)
                addFileToViewModel(uri)
            }
        }
    }

    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            previewImage?.setImageURI(uri)
            tmpFile = File.createTempFile("tmp_image_file", ".png", activity?.cacheDir).apply {
                createNewFile()
            }
            try{
                val inputStream = activity?.contentResolver?.openInputStream(uri)
                Files.copy(inputStream, tmpFile!!.toPath(), StandardCopyOption.REPLACE_EXISTING)
            }catch(e: Exception){
                Log.d("addbookfragment", e.localizedMessage)
            }

            addFileToViewModel(uri)
        }
    }

    private var latestTmpUri: Uri? = null
    private var tmpFile: File? = null

    private val previewImage by lazy { view?.findViewById<ImageView>(R.id.image_preview) }

    private fun addFileToViewModel(uri: Uri){
        viewModel.image.value = tmpFile
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBookBinding.inflate(inflater, container, false)

        viewModel.accessToken.value = activity?.getSharedPreferences("user_information", Context.MODE_PRIVATE)?.getString(getString(R.string.access_token_key), "")
        viewModel.userId.value = activity?.getSharedPreferences("user_information", Context.MODE_PRIVATE)?.getString(getString(R.string.user_id_key), "")

        viewModel.status.observe(viewLifecycleOwner, { bookStatus ->

            findNavController().navigate(R.id.action_addBookFragment_to_navigation_profile)
        })
        _binding.viewModel = viewModel
        _binding.lifecycleOwner = this
        _binding.fragment = this

        setObservers()
        return _binding.root
    }

    fun takeImage() {
        lifecycleScope.launchWhenStarted {
            getTmpFileUri().let { uri ->
                latestTmpUri = uri
                takeImageResult.launch(uri)
            }
        }
    }

    fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    private fun getTmpFileUri(): Uri {
        tmpFile = File.createTempFile("tmp_image_file", ".png", activity?.cacheDir).apply {
            createNewFile()
        }

        return FileProvider.getUriForFile(requireContext(), "${BuildConfig.APPLICATION_ID}.provider", tmpFile!!)
    }

    private fun setObservers(){
        viewModel.toastMessage.observe(viewLifecycleOwner, {
            Snackbar.make(binding.root, it.toString(), Snackbar.LENGTH_LONG).show()
        })
    }

    fun onTitleChanged(s: CharSequence, start: Int, before: Int, count: Int){
        viewModel.title.value = s.toString()
    }
    fun onAuthorChanged(s: CharSequence, start: Int, before: Int, count: Int){
        viewModel.author.value = s.toString()
    }
    fun onDescChanged(s: CharSequence, start: Int, before: Int, count: Int){
        viewModel.description.value = s.toString()
    }
    fun onTagsChanged(s: CharSequence, start: Int, before: Int, count: Int){
        viewModel.tags.value = s.toString()
    }
    fun onGenreChanged(s: CharSequence, start: Int, before: Int, count: Int){
        viewModel.genre.value = s.toString()
    }

}