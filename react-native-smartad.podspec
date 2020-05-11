require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-smartad"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-smartad
                   DESC
  s.homepage     = "https://github.com/RedPillGroup/react-native-smartad"
  s.license      = "MIT"
  # s.license    = { :type => "MIT", :file => "FILE_LICENSE" }
  s.authors      = { "bsisic" => "baptiste@redpillparis.com" }
  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/RedPillGroup/react-native-smartad.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,m,swift}"
  s.requires_arc = true

  s.dependency "React"
  s.dependency "Smart-Display-SDK"
  # ...
  # s.dependency "..."
end