package com.deniscerri.ytdl.settings

/**
 * CORE: All preference keys with byte-for-byte identical names and defaults from Android app.
 * Extracted from preference XML files.
 */
object PreferenceKeys {
    // General settings
    const val APP_LANGUAGE = "app_language" // default: ""
    const val THEME = "ytdlnis_theme" // default: "System"
    const val ACCENT = "theme_accent" // default: "Default"
    const val HIGH_CONTRAST = "high_contrast" // default: false
    const val PREFERRED_DOWNLOAD_TYPE = "preferred_download_type" // default: "auto"
    const val REMEMBER_DOWNLOAD_TYPE = "remember_download_type" // default: false
    const val LAST_USED_DOWNLOAD_TYPE = "last_used_download_type" // default: "auto"
    const val PREFERRED_HOME_SCREEN = "start_destination" // default: "Home"
    const val SEARCH_ENGINE = "search_engine" // default: "ytsearch"
    const val SEARCH_SUGGESTIONS = "search_suggestions" // default: true
    const val VIDEO_RECOMMENDATIONS = "video_recommendations" // default: false
    const val SWIPE_GESTURES = "swipe_gestures" // default: true
    const val SWIPE_GESTURES_DOWNLOAD_CARD = "swipe_gestures_download_card" // default: true
    const val HIDE_THUMBNAILS = "hide_thumbnails" // default: false
    const val LABEL_VISIBILITY = "label_visibility" // default: "always"
    const val SHOW_DOWNLOAD_COUNT = "show_download_count" // default: true
    const val LAYOUT = "layout" // default: "list"
    const val APP_ICON = "app_icon" // default: "default"

    // Folder settings
    const val MUSIC_PATH = "music_path" // default: platform-specific
    const val VIDEO_PATH = "video_path" // default: platform-specific
    const val COMMAND_PATH = "command_path" // default: platform-specific
    const val CACHE_DIRECTORY = "cache_dir" // default: ""
    const val DOWNLOAD_ARCHIVE_FOLDER = "download_archive" // default: ""

    // Downloading settings
    const val SHOW_DOWNLOAD_CARD = "download_card" // default: true
    const val QUICK_DOWNLOAD = "quick_download" // default: false
    const val UPDATE_FORMATS = "update_formats" // default: false
    const val UPDATE_FORMATS_BACKGROUND = "update_formats_background" // default: false
    const val CONCURRENT_DOWNLOADS = "concurrent_downloads" // default: 1
    const val CONCURRENT_FRAGMENTS = "concurrent_fragments" // default: 1
    const val LIMIT_RATE = "limit_rate" // default: ""
    const val USE_ARIA2 = "aria2" // default: false
    const val RETRIES = "retries" // default: ""
    const val FRAGMENT_RETRIES = "fragment_retries" // default: ""
    const val DOWNLOAD_OVER_METERED = "metered_networks" // default: true
    const val CACHE_DOWNLOADS = "cache_downloads" // default: true
    const val KEEP_CACHE = "keep_cache" // default: false
    const val USE_SCHEDULER = "use_scheduler" // default: false
    const val SCHEDULER_START = "scheduler_start" // default: 0
    const val SCHEDULER_END = "scheduler_end" // default: 0
    const val PREVENT_DUPLICATE_DOWNLOADS = "prevent_duplicate_downloads" // default: ""
    const val INCOGNITO = "incognito" // default: false
    const val LOG_DOWNLOADS = "log_downloads" // default: false
    const val NO_DOWNLOAD_FRAGMENTS = "no_download_fragments" // default: false
    const val BUFFER_SIZE = "buffer_size" // default: ""

    // Processing settings - Audio
    const val AUDIO_FORMAT = "audio_format" // default: ""
    const val AUDIO_QUALITY = "audio_quality" // default: "0"
    const val AUDIO_BITRATE = "audio_bitrate" // default: ""
    const val FILE_NAME_TEMPLATE_AUDIO = "file_name_template_audio" // default: "%(uploader).30B - %(title).170B"
    const val EMBED_THUMBNAIL = "embed_thumbnail" // default: true
    const val CROP_THUMBNAIL = "crop_thumbnail" // default: true
    const val PREFERRED_AUDIO_CODEC = "audio_codec" // default: ""
    const val PREFER_CONTAINER_OVER_CODEC_AUDIO = "prefer_container_over_codec_audio" // default: false
    const val PREFER_DRC_AUDIO = "prefer_drc_audio" // default: false
    const val PLAYLIST_AS_ALBUM = "playlist_as_album" // default: true

    // Processing settings - Video
    const val VIDEO_FORMAT = "video_format" // default: ""
    const val VIDEO_QUALITY = "video_quality" // default: "best"
    const val FILE_NAME_TEMPLATE = "file_name_template" // default: "%(uploader).30B - %(title).170B"
    const val PREFERRED_VIDEO_CODEC = "video_codec" // default: ""
    const val VIDEO_EMBED_THUMBNAIL = "video_embed_thumbnail" // default: false
    const val ADD_CHAPTERS = "add_chapters" // default: true
    const val EMBED_SUBTITLES = "embed_subtitles" // default: true
    const val WRITE_SUBTITLES = "write_subtitles" // default: false
    const val WRITE_AUTO_SUBTITLES = "write_auto_subtitles" // default: false
    const val SUBS_LANG = "subs_lang" // default: "en.*,.*-orig"
    const val SUBTITLE_FORMAT = "sub_format" // default: ""
    const val NO_KEEP_SUBS = "no_keep_subs" // default: true
    const val WRITE_SUBS_WHEN_EMBED_SUBS = "write_subs_when_embed_subs" // default: false
    const val RECODE_VIDEO = "recode_video" // default: false
    const val VIDEO_COMPATIBLE = "video_compatible" // default: false

    // Processing settings - General
    const val WRITE_THUMBNAIL = "write_thumbnail" // default: false
    const val THUMBNAIL_FORMAT = "thumbnail_format" // default: "jpg"
    const val WRITE_DESCRIPTION = "write_description" // default: false
    const val EMBED_METADATA = "embed_metadata" // default: true
    const val RESTRICT_FILENAMES = "restrict_filenames" // default: false
    const val ENABLE_MTIME = "mtime" // default: false
    const val SPONSORBLOCK_FILTERS = "sponsorblock_filters" // default: emptySet
    const val SPONSORBLOCK_API_URL = "sponsorblock_api_url" // default: "https://sponsor.ajay.app"
    const val FORCE_KEYFRAMES = "force_keyframes" // default: false
    const val FORMAT_IMPORTANCE = "format_importance" // default: []
    const val USE_FORMAT_SORTING = "use_format_sorting" // default: false
    const val PREFERRED_FORMAT_SIZE = "preferred_format_size" // default: ""

    // Processing settings - Extra commands
    const val USE_EXTRA_COMMANDS = "use_extra_commands" // default: true
    const val EXTRA_COMMAND = "extra_command" // default: ""

    // Updating settings
    const val UPDATE_APP = "update_app" // default: false
    const val UPDATE_APP_BETA = "update_app_beta" // default: false
    const val AUTO_UPDATE_YTDLP = "auto_update_ytdlp" // default: false
    const val YTDL_SOURCE = "ytdl_source" // default: "stable"
    const val UPDATE_YTDLP_WHILE_DOWNLOADING = "update_ytdlp_while_downloading" // default: false
    const val AUTOMATIC_BACKUP = "automatic_backup" // default: true
    const val BACKUP_PATH = "backup_path" // default: ""

    // Advanced settings
    const val USE_COOKIES = "use_cookies" // default: false
    const val USE_HEADER = "use_header" // default: false
    const val USERAGENT_HEADER = "useragent_header" // default: ""
    const val PROXY = "proxy" // default: ""
    const val FORCE_IPV4 = "force_ipv4" // default: false
    const val SOCKET_TIMEOUT = "socket_timeout" // default: "5"
    const val NO_CHECK_CERTIFICATES = "no_check_certificates" // default: false
    const val DISABLE_WRITE_INFO_JSON = "disable_write_info_json" // default: false
    const val DATA_FETCHING_EXTRA_COMMAND = "data_fetching_extra_command" // default: false
    const val NO_FLAT_PLAYLIST = "no_flat_playlist" // default: false
    const val USE_ITEM_URL_NOT_PLAYLIST = "use_item_url_not_playlist" // default: false
    const val USE_ORIGINAL_URL_PLAYLIST = "use_original_url_playlist" // default: false
    const val USE_APP_LANGUAGE_FOR_METADATA = "use_app_language_for_metadata" // default: true
    const val DATA_FETCHING_EXTRACTOR_YOUTUBE = "youtube_extractor" // default: "newpipe"
    const val YOUTUBE_PLAYER_CLIENTS = "youtube_player_clients" // default: "[]"
    const val YOUTUBE_GENERATED_PO_TOKENS = "youtube_generated_po_tokens" // default: "[]"
    const val YOUTUBE_DATA_SYNC_ID = "youtube_data_sync_id" // default: ""
    const val YOUTUBE_OTHER_EXTRACTOR_ARGS = "youtube_other_extractor_args" // default: ""
    const val SHOW_TERMINAL = "show_terminal" // default: false
    const val SHOW_QUICK_DOWNLOAD_SHARE_MENU = "show_quick_download_share_menu" // default: false
    const val DISPLAY_OVER_APPS = "display_over_apps" // default: false
    const val USE_ALARM_MANAGER = "use_alarm_manager" // default: false
    const val PREFERRED_FORMAT_ID = "format_id" // default: ""
    const val PREFERRED_FORMAT_ID_AUDIO = "format_id_audio" // default: ""
    const val PREFERRED_AUDIO_LANGUAGE = "audio_language" // default: ""
    const val PREFERRED_LOCALE = "locale" // default: ""
    const val CLEANUP_LEFTOVER_DOWNLOADS = "cleanup_leftover_downloads" // default: false
}
