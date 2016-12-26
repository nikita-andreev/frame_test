require 'capybara/rspec'
require 'eyes_selenium'
require 'applitools/capybara'

Applitools.register_capybara_driver browser: :chrome

Capybara.configure do |c|
  c.run_server = false
  c.app_host = 'https://aqueous-shore-68375.herokuapp.com'
end

RSpec.describe "Capybara", type: :feature, sauce: false do
  let(:eyes) do
    Applitools::Selenium::Eyes.new.tap do |e|
      e.api_key = ENV['APPLITOOLS_API_KEY']
      e.log_handler = Logger.new(STDOUT)
    end

  end

  it do
    eyes.open(
      driver: page.driver,
      app_name: 'frames_111',
      test_name: 'frames_test_222',
      viewport_size: {width: 1024, height: 440}
    )
    begin
      visit '/frames'
      eyes.check_window('First page with all frames')
      page.execute_script "window.scrollBy(0,30)"
      eyes.check_frame(name_or_id: 'frame_to_test', tag: 'test frame0')
      eyes.check_frame(frames_path: ['frame_to_test', 'frame_to_test'], tag: 'test frame1')
      eyes.close
    ensure
      eyes.abort_if_not_closed
    end
  end
end

