package com.mastercard.creditorcommerce.library.exception

/*
* LibraryError errors and custom errorCode and error messages for creditor commerce library.
*/
internal enum class LibraryErrorType(val libraryError: LibraryError) {

    InvalidDspID(LibraryError(1001, "Dsp id is invalid.")),
    NoDataPresentForDspID(LibraryError(1001, "Dsp id is invalid.")),
    InvalidLifecycleId(LibraryError(1002, "Lifecycle id is invalid.")),
    InvalidBusinessType(LibraryError(1003, "Business type is invalid.")),
    InvalidJourneyType(LibraryError(1004, "Journey type is invalid")),
    InvalidDspManifestUrl(LibraryError(1005, "Unable to reach to configured URL. Please check and reconfigure dsp manifest file URL.")),
    InvalidSecureProtocol(LibraryError(1006, "Invalid protocol, Secure protocol HTTPS is only supported.")),

    InvalidManifestFileError(LibraryError(1007, "Invalid dsp manifest file. Please recheck & configure again.")),

    EmptyStringReceived(InvalidManifestFileError.libraryError),
    InvalidEncoding(InvalidManifestFileError.libraryError),
    DSPListFileIsEmpty(InvalidManifestFileError.libraryError),
    InvalidDSPName(InvalidManifestFileError.libraryError),
    InvalidDSPLogo(InvalidManifestFileError.libraryError),
    InvalidDSPUniversalLink(InvalidManifestFileError.libraryError),
    InvalidDSPUniqueId(InvalidManifestFileError.libraryError),
    InvalidDspLogoHash(InvalidManifestFileError.libraryError),

    //Signed manifest & signature validation
    InvalidManifestDataReceived(InvalidManifestFileError.libraryError),
    EmptyPayloadReceived(InvalidManifestFileError.libraryError),
    EmptySigningCertificateReceived(InvalidManifestFileError.libraryError),
    InvalidSignatureCertificateUrl(InvalidManifestFileError.libraryError),
    InvalidSignedCertificate(InvalidManifestFileError.libraryError),
    SignatureVerificationFailed(InvalidManifestFileError.libraryError),
    InvalidPublicKey(InvalidManifestFileError.libraryError),
    EmptyHeaderReceived(InvalidManifestFileError.libraryError),
    EmptySignatureReceived(InvalidManifestFileError.libraryError),
    InvalidSignatureAlgorithm(InvalidManifestFileError.libraryError),

    //Android Specific
    UnableToInvokeDSP(LibraryError(1101, "Unable to open DSP app.")),

    //API Error
    TimeoutError(LibraryError(1102, "Technical error occurred request timeout.")),
    NoConnectionError(LibraryError(1103, "Network connection not available. Please try to connect before using.")),
    ClientError(LibraryError(1105, "Unable to reach to configured URL. Please recheck & configure again.")),
    UnknownTechnicalError(InvalidManifestFileError.libraryError),
}
