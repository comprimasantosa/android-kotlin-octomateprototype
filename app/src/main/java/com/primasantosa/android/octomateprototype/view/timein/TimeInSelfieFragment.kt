package com.primasantosa.android.octomateprototype.view.timein

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.RoundedCornersTransformation
import com.primasantosa.android.octomateprototype.R
import com.primasantosa.android.octomateprototype.databinding.FragmentTimeInSelfieBinding
import com.primasantosa.android.octomateprototype.util.UIUtil
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TimeInSelfieFragment : Fragment() {
    private var _binding: FragmentTimeInSelfieBinding? = null
    private val binding
        get() = _binding!!
    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimeInSelfieBinding.inflate(inflater, container, false)

        initUI()
        requestCameraPermission()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun initUI() {
        UIUtil.setToolbarTitle(activity, resources.getString(R.string.label_time_in))
        UIUtil.hideDrawer(activity)
        binding.tvNext.setOnClickListener {
            goToTimeInLocation()
        }
        binding.imgShutter.setOnClickListener { takePhoto() }
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun goToTimeInLocation() {
        val destination =
            TimeInSelfieFragmentDirections.actionTimeInSelfieFragmentToTimeInLocationFragment()
        findNavController().navigate(destination)
    }

    private fun showPermissionError() {
        binding.containerPreview.visibility = View.GONE
        binding.imgShutter.visibility = View.GONE

        Toast.makeText(
            requireContext(),
            getString(R.string.error_missing_camera_permission),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun requestCameraPermission() {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    startCamera()
                } else {
                    showPermissionError()
                }
            }

        when {
            isPermissionGranted() -> {
                startCamera()
            }
            shouldShowRequestPermissionRationale(REQUIRED_PERMISSIONS[0]) -> {
                showPermissionError()
            }
            else -> {
                requestPermissionLauncher.launch(
                    REQUIRED_PERMISSIONS[0]
                )
            }
        }
    }

    private fun isPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(e: ImageCaptureException) {
                    Timber.e(e)
                }
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    binding.imgPreview.load(photoFile) {
                        transformations(RoundedCornersTransformation(16f))
                    }
                    binding.tvNext.visibility = View.VISIBLE
                }
            })
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewCam.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder()
                .build()
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun getOutputDirectory(): File {
        val mediaDir = requireActivity().externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireActivity().filesDir
    }

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}